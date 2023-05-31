import React, { useEffect, useRef, useState } from "react";


const Video = ({data}) => {
    const ref = useRef();

    useEffect(() => {
        data.peer.on("stream", stream => {
            ref.current.srcObject = stream;
        });
    }, [data]);

    return (
      <div className="video-tile">
        <video playsInline autoPlay ref={ref} />
        <div className="participant-name">
              <div className="name">{data.peerFullName}</div>
              {ref.current && ref.current.srcObject && 
                <div className="status">
                  {data.mic === "off" && <i className="bi bi-mic-mute-fill"></i>}
                  {data.camera === "off" && <i className="bi bi-camera-video-off-fill"></i>}
                </div>
              }
            </div>
      </div>
    );
};

const VideoBox = ({roomId, userData, stompClient}) => {
    const [peers, setPeers] = useState([]);
    const peersRef = useRef([]);
    const userVideo = useRef();

    const [cameraMuted, setCameraMuted] = useState(false);
    const [audioMuted, setAudioMuted] = useState(false);


    useEffect(() => {
        let userStream = null;
        navigator.mediaDevices.getUserMedia({ video: true, audio: true }).then(stream => {
            userVideo.current.srcObject = stream;
            userStream = stream;

            onConnected();
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
      const peers = [];
      users.forEach(userGot => {
          const peer = createPeer(userGot.userId, userData.id);
          peersRef.current.push({
              peerID: userGot.userId,
              peer: peer,
          });
          peers.push({
            peerID: userGot.userId,
            peerFullName: `${userGot.firstName} ${userGot.lastName}`,
            peer: peer
          });
      });
      setPeers(peers);
  };

  const handleUserJoined = (message) => {
      const payload = JSON.parse(message.body);
      const peer = addPeer(payload.signal, payload.callerID);
      peersRef.current.push({
          peerID: payload.callerID,
          peer: peer,
      });
      setPeers(users => [...users, {
        peerID: payload.callerID,
        peerFullName: `${payload.extraUserInfo.firstName} ${payload.extraUserInfo.lastName}`,
        peer: peer,
    }]);
  };

  const handleUserLeft = (message) => {
    const payload = JSON.parse(message.body);
    const peer = peersRef.current.find(p => p.peerID === payload.id);
    if(peer){
      peer.peer.destroy();
    }

    const peers = peersRef.current.filter(p => p.peerID !== payload.id);
    peersRef.current = peers;
    setPeers(peers);
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

  function handleEvents(data){
    const decoder = new TextDecoder('utf-8');
    const jsonString = decoder.decode(data);

    const jsonData = JSON.parse(jsonString);
    setPeers(prevPeers => {
      const updatedPeers = prevPeers.map(peer => {
        if (peer.peerID === jsonData.userId) {
          switch (jsonData.type) {
            case "microphone_on":
              return { ...peer, mic: "on" };
            case "microphone_off":
              return { ...peer, mic: "off" };
            case "camera_on":
              return { ...peer, camera: "on" };
            case "camera_off":
              return { ...peer, camera: "off" };
            default:
              return peer;
          }
        }
        return peer;
      });
      return updatedPeers;
    });
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
                  <Video key={peer.peerID} data = {peer}/>
              );
          })}
        </div>
        <div className="buttons">
          <div className="buttonsPad">
            <div onClick={muteMic} className="btn btn-primary">{audioMuted? <i className="bi bi-mic-mute-fill"></i> : <i className="bi bi-mic-fill"></i>}</div>
            <div onClick={muteCam} className="btn btn-primary">{cameraMuted? <i className="bi bi-camera-video-off-fill"></i> : <i className="bi bi-camera-video-fill"></i>}</div>
          </div>
        </div>
      </div>
  );
};

export default VideoBox;
