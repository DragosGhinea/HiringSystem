import React, { useEffect, useRef, useState } from "react";
import { useNavigate } from 'react-router-dom';
import jwtInterceptor from "../../../components/shared/JwtInterceptor";
import UsersModal from "./UsersModal";
import CloseInterviewModal from "./CloseInterviewModal";

const Video = ({data, remoteStream}) => {
    const ref = useRef();
    const [isAudioActive, setIsAudioActive] = useState(false);

    useEffect(() => {
      let audioContext = null;
      let analyser = null;
      let source = null;

      ref.current.srcObject = remoteStream.stream;

      if(remoteStream){
        audioContext = new AudioContext();
        analyser = audioContext.createAnalyser();
        source = audioContext.createMediaStreamSource(remoteStream.stream);

        source.connect(analyser);

        const bufferLength = analyser.frequencyBinCount;
        const dataArray = new Uint8Array(bufferLength);

        const analyzeAudio = () => {
          analyser.getByteFrequencyData(dataArray);

          const average = dataArray.reduce((sum, value) => sum + value, 0) / bufferLength;
          setIsAudioActive(average > 0.5); 

          requestAnimationFrame(analyzeAudio);
        };

        analyzeAudio();
      }

      return () => {
        analyser?.disconnect();
        source?.disconnect();
        audioContext?.close();
      }
    }, [remoteStream]);

    useEffect(() => {
      let audioContext = null;
      let analyser = null;
      let source = null;

      if(ref.current && ref.current.srcObject){
        audioContext = new AudioContext();
        analyser = audioContext.createAnalyser();
        source = audioContext.createMediaStreamSource(ref.current.srcObject);

        source.connect(analyser);

        const bufferLength = analyser.frequencyBinCount;
        const dataArray = new Uint8Array(bufferLength);

        const analyzeAudio = () => {
          analyser.getByteFrequencyData(dataArray);

          const average = dataArray.reduce((sum, value) => sum + value, 0) / bufferLength;
          setIsAudioActive(average > 0.5); 

          requestAnimationFrame(analyzeAudio);
        };

        analyzeAudio();
      }

      return () => {
        analyser?.disconnect();
        source?.disconnect();
        audioContext?.close();
      }
    }, [data, ref.current?.srcObject])

    return (
      <div className={`video-tile ${isAudioActive && 'audio-active'}`}>
        <video playsInline autoPlay ref={ref} />
        <div className="participant-name">
              <div className="name">{data.peerFullName}</div>
              {ref.current && ref.current.srcObject && 
                <div className="status">
                  {remoteStream.mic === "off" && <i className="bi bi-mic-mute-fill"></i>}
                  {remoteStream.camera === "off" && <i className="bi bi-camera-video-off-fill"></i>}
                </div>
              }
            </div>
      </div>
    );
};

const VideoBox = ({muted, cameraOff, roomId, userData, stompClient, participantData}) => {
    const [usersModalOpen, setUsersModalOpen] = useState(false);
    const [confirmCloseModalOpen, setConfirmCloseModalOpen] = useState(false);
    const [peers, setPeers] = useState([]);
    const peersRef = useRef([]);
    const userVideo = useRef();
    const [remoteStreams, setRemoteStreams] = useState({})

    const [cameraMuted, setCameraMuted] = useState(false);
    const [audioMuted, setAudioMuted] = useState(false);
    const cameraMutedRef = useRef(false);
    const audioMutedRef = useRef(false);
    const navigate = useNavigate();


    useEffect(() => {
        let userStream = null;
        navigator.mediaDevices.getUserMedia({ video: true, audio: true }).then(stream => {
            userVideo.current.srcObject = stream;
            userStream = stream;

            if(muted)
              muteMic();
            if(cameraOff)
              muteCam();

            onConnected();
        })
        .catch(err => {
          console.log(err);
        })

        return () => {
            const tracks = userStream?.getTracks();
            tracks.forEach(track => track.stop());

            peersRef.current.forEach((peer) => {
              peer.peer.destroy();
            });
        };
    }, [roomId]);

    const onConnected = () => {

      stompClient.subscribe(`/api/v1/user/sockets/${userData.id}/interview/room/force-action`, forcedReceived);

      stompClient.subscribe(`/api/v1/user/sockets/${userData.id}/interview/room/users`, handleAllUsers);

      stompClient.subscribe(`/api/v1/user/sockets/${userData.id}/interview/room/user-joined`, handleUserJoined);

      stompClient.subscribe(`/interview/room/video/message/${roomId}/user-left`, handleUserLeft);

      stompClient.subscribe(`/api/v1/user/sockets/${userData.id}/interview/room/receiving-returned-signal`, handleReceivingReturnedSignal);

      stompClient.publish({
        destination: `/api/v1/sockets/interview/room/video/message/${roomId}/join`,
        body: JSON.stringify({ userId: userData.id }),
        skipContentLengthHeader: true,
      });
    };

  const handleAllUsers = (message) => {
      const users = JSON.parse(message.body);
      users.forEach(userGot => {
          const peer = createPeer(userGot.userId, userData.id);
          peer.on('stream', (stream) => {
            setRemoteStreams(prevStreams => {
              return {...prevStreams, [userGot.userId]: {
                stream: stream,
                mic: "on",
                camera: "on"
              }}
            })
          });

          peer.on('connect', () => {
            peer.send(JSON.stringify({type: 'status_request', userId: userData.id}));

            setPeers(prevPeers => [...prevPeers, 
              {
                peerID: userGot.userId,
                peerFullName: `${userGot.firstName} ${userGot.lastName}`,
                peer: peer
              }
            ]);
          })

          peersRef.current.push({
            peerID: userGot.userId,
            peer: peer,
          });
      });
  };

  const handleUserJoined = (message) => {
      const payload = JSON.parse(message.body);
      const peer = addPeer(payload.signal, payload.callerID);

      peer.on('stream', (stream) => {
        setRemoteStreams(prevStreams => {
          return {...prevStreams, [payload.callerID]: {
            stream: stream,
            mic: "on",
            camera: "on"
          }}
        })
      });

      peer.on('connect', () => {
        peer.send(JSON.stringify({type: 'status_request', userId: userData.id}));

        setPeers(users => [...users, {
          peerID: payload.callerID,
          peerFullName: `${payload.extraUserInfo.firstName} ${payload.extraUserInfo.lastName}`,
          peer: peer,
        }]);
      })

      peersRef.current.push({
        peerID: payload.callerID,
        peer: peer,
      });
  };

  const handleUserLeft = (message) => {
    const payload = JSON.parse(message.body);
    const peer = peersRef.current.find(p => p.peerID === payload.id);
    if(peer){
      peer.peer.destroy();
    }

    const peers = peersRef.current.filter(p => p.peerID !== payload.id);
    peersRef.current = peers;
    setPeers((prevPeers) => prevPeers.filter((peer) => peer.peerID !== payload.id));

    setRemoteStreams((prevStreams) => {
      const updatedStreams = { ...prevStreams };
      delete updatedStreams[payload.id];
      return updatedStreams;
    });
};

  const handleReceivingReturnedSignal = (message) => {
      const payload = JSON.parse(message.body);
      const item = peersRef.current.find(p => p.peerID === payload.id);
      item.peer.signal(payload.signal);
  };

  const createPeer = (userToSignal, callerID) => {

      const peer = new window.SimplePeer({
          initiator: true,
          trickle: false,
          stream: userVideo.current.srcObject,
      });

      peer.on("signal", signal => {
          stompClient.publish({
            destination: `/api/v1/sockets/interview/room/video/message/${roomId}/signal`,
            body: JSON.stringify({ userToSignal: userToSignal, callerID: callerID, signal: signal }),
          });
      });

      peer.on('data', data => {
        handleEvents(data);
      })

      return peer;
  };

  const addPeer = (incomingSignal, callerID) => {
      const peer = new window.SimplePeer({
          initiator: false,
          trickle: false,
          stream: userVideo.current.srcObject,
      });

      peer.on("signal", signal => {
          stompClient.publish({
            destination: `/api/v1/sockets/interview/room/video/message/${roomId}/return-signal`,
            body: JSON.stringify({ signal: signal, callerID: callerID, id: userData.id }),
          });
      });

      peer.on('data', data => {
        handleEvents(data);
      })

      peer.signal(incomingSignal);

      return peer;
  };

  const handleEvents = (data) => {
    const decoder = new TextDecoder('utf-8');
    const jsonString = decoder.decode(data);
    const jsonData = JSON.parse(jsonString);

    if(jsonData.type === "status_request"){
      const peer = peersRef.current.find(peer => peer.peerID === jsonData.userId);
      const toSend = JSON.stringify({
        type: "status_response",
        mic: audioMutedRef.current ? "off" : "on",
        camera: cameraMutedRef.current ? "off" : "on",
        userId: userData.id
      });
      peer.peer.send(toSend);
      return;
    }
  
    setRemoteStreams((prevStreams) => {
      const updatedStreams = { ...prevStreams };
      const stream = updatedStreams[jsonData.userId];
  
      if (stream) {
        switch (jsonData.type) {
          case 'microphone_on':
            stream.mic = 'on';
            break;
          case 'microphone_off':
            stream.mic = 'off';
            break;
          case 'camera_on':
            stream.camera = 'on';
            break;
          case 'camera_off':
            stream.camera = 'off';
            break;
          case 'status_response':
            stream.camera = jsonData.camera;
            stream.mic = jsonData.mic;
            break;
          default:
            break;
        }
      }
  
      return updatedStreams;
    });
  }

  const forcedReceived = (message) => {
    const payload = JSON.parse(message.body);

    if(payload.type === "MUTE"){
      const audios = userVideo.current.srcObject.getAudioTracks();
      audios.forEach(track => track.enabled = false);

      peersRef.current.forEach((peer) => {
        console.log("IS SENDING")
        peer.peer.send(JSON.stringify({userId: userData.id, type: "microphone_off"}))
      });

      setAudioMuted(true);
      audioMutedRef.current = true;
    }
    else if(payload.type === "CAMERA_OFF"){
      const videos = userVideo.current.srcObject.getVideoTracks();
      videos.forEach(track => track.enabled = false);

      peersRef.current.forEach((peer) => {
        peer.peer.send(JSON.stringify({userId: userData.id, type: "camera_off"}))
      });

      setCameraMuted(true);
      cameraMutedRef.current = true;
    }
    else if(payload.type === "KICK"){
      navigate("/interview/room/left?kicked=true");
    }
  }

  function muteMic() {
    if(audioMuted){
      const audios = userVideo.current.srcObject.getAudioTracks();
      audios.forEach(track => track.enabled = true);

      peersRef.current.forEach((peer) => {
        peer.peer.send(JSON.stringify({userId: userData.id, type: "microphone_on"}))
      });
    }
    else{
      const audios = userVideo.current.srcObject.getAudioTracks();
      audios.forEach(track => track.enabled = false);

      peersRef.current.forEach((peer) => {
        peer.peer.send(JSON.stringify({userId: userData.id, type: "microphone_off"}))
      });
    }

    setAudioMuted(!audioMuted);
    audioMutedRef.current = !audioMutedRef.current;
  }

  async function muteCam() {
    if(cameraMuted){
      const videos = userVideo.current.srcObject.getVideoTracks();
      videos.forEach(track => track.enabled = true);

      peersRef.current.forEach((peer) => {
        peer.peer.send(JSON.stringify({userId: userData.id, type: "camera_on"}))
      });
    }
    else{
      const videos = userVideo.current.srcObject.getVideoTracks();
      videos.forEach(track => track.enabled = false);

      peersRef.current.forEach((peer) => {
        peer.peer.send(JSON.stringify({userId: userData.id, type: "camera_off"}))
      });

    }

    setCameraMuted(!cameraMuted);
    cameraMutedRef.current = !cameraMutedRef.current;
  }

  function leave(){
    navigate("/interview/room/left");
  }

  function closeRoom(){
    jwtInterceptor.post(`http://localhost:8081/api/v1/interview/closeRoom/${roomId}`);
  }


  return (
      <div className="videobox">
        <div className="videos">
          <div className="video-tile">
            <video muted ref={userVideo} autoPlay playsInline />
            <div className="participant-name">
              <div className="name">Myself</div>
              <div className="status">
                {audioMuted && <i className="bi bi-mic-mute-fill"></i>}
                {cameraMuted && <i className="bi bi-camera-video-off-fill"></i>}
              </div>
            </div>
          </div>
          {peers.map((peer) => {
              return (
                  <Video key={peer.peerID} data = {peer} remoteStream = {remoteStreams[peer.peerID]}/>
              );
          })}
        </div>
        {participantData.isRoomModerator && <div className="moderator-buttons">
          <div className="buttonsPad">
            <div onClick={() => setUsersModalOpen(true)}className="btn btn-danger">Users <i className="bi bi-people-fill"></i></div>
            <div onClick={() => setConfirmCloseModalOpen(true)} className="btn btn-danger">Close Interview <i className="bi bi-x-circle"></i></div>
          </div>
        </div>
        }
        <div className="buttons">
          <div className="buttonsPad">
            <div onClick={muteMic} className="btn btn-primary">{audioMuted? <i className="bi bi-mic-mute-fill"></i> : <i className="bi bi-mic-fill"></i>}</div>
            <div onClick={muteCam} className="btn btn-primary">{cameraMuted? <i className="bi bi-camera-video-off-fill"></i> : <i className="bi bi-camera-video-fill"></i>}</div>
            <div onClick={leave} className="btn btn-primary"><i className="bi bi-box-arrow-right"></i></div>
          </div>
        </div>

        {usersModalOpen && <UsersModal roomId = {roomId} peers = {peers} streams = {remoteStreams} openModal={usersModalOpen} setOpenModal={setUsersModalOpen} />}
        {confirmCloseModalOpen && <CloseInterviewModal closeMethod={closeRoom} setOpenModal={setConfirmCloseModalOpen}/>}
      </div>
  );
};

export default VideoBox;
