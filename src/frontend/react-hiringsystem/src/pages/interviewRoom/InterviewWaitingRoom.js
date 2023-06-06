import React, { useEffect, useState, useRef } from 'react';
import { useParams } from 'react-router-dom';
import '../css/interviewWaitingRoom.css'
import jwtInterceptor from "../../components/shared/JwtInterceptor";
import InterviewRoomPage from './InterviewRoomPage';
import InterviewNotFound from './InterviewNotFound';
import useCountdown from '../../components/countdown/useCountdown';
import CountdownBox from '../../components/countdown/CountdownBox';

const InterviewWaitingRoom = () => {
    const { id } = useParams();
    const [audioMuted, setAudioMuted] = useState();
    const [cameraMuted, setCameraMuted] = useState();
    const videoPreview = useRef();
    const [interviewParticipant, setInterviewParticipant] = useState();
    const [isReady, setReady] = useState();
    const [loadedStream, setLoadedStream] = useState(false);
    const [untilStart, setUntilStart] = useState(-1);
    const [days, hours, minutes, seconds] = useCountdown(untilStart);

    useEffect(() => {
        jwtInterceptor.post(`http://localhost:8081/api/v1/interview/getParticipantInfo/${id}`)
            .then(data => {
                setInterviewParticipant(data.data);
            })
            .catch(err => {
                console.log(err);
            });

        jwtInterceptor.get(`http://localhost:8081/api/v1/interview/getUntilStart/${id}`)
            .then(data => {
                if(data.data<=0)
                    setUntilStart(0);
                setUntilStart(data.data);
            })
            .catch(err => {
                console.log(err);
            });

    }, []);

    useEffect(() => {
        if(interviewParticipant && days+hours+minutes+seconds===0){
            let userStream = null;

            navigator.mediaDevices.getUserMedia({ video: true, audio: true }).then(stream => {
                videoPreview.current.srcObject = stream;
                userStream = stream;
                setLoadedStream(true);
            })
            .catch(err => {
              console.log(err);
            })
    
            return () => {
                const tracks = userStream?.getTracks();
                tracks?.forEach(track => track.stop());
            };
        }
    }, [interviewParticipant, seconds]);

    if(days+hours+minutes+seconds>0){
        return (
            <div className="row">
                <div className="col-8 offset-2 mt-5">
                    <center>
                        <h2>Someone is a little early</h2>
                        <p>The interview will start at the scheduled time.</p>
                    </center>
                    <CountdownBox title="You can join the interview in:" days={days} hours={hours} minutes={minutes} seconds={seconds}/>
                </div>
            </div>
        );
    }

    function muteMic() {
        if(audioMuted){
          const audios = videoPreview.current.srcObject.getAudioTracks();
          audios.forEach(track => track.enabled = true);
        }
        else{
          const audios = videoPreview.current.srcObject.getAudioTracks();
          audios.forEach(track => track.enabled = false);
        }
    
        setAudioMuted(!audioMuted);
      }
    
      async function muteCam() {
        if(cameraMuted){
          const videos = videoPreview.current.srcObject.getVideoTracks();
          videos.forEach(track => track.enabled = true);
        }
        else{
          const videos = videoPreview.current.srcObject.getVideoTracks();
          videos.forEach(track => track.enabled = false);
    
        }
    
        setCameraMuted(!cameraMuted);
      }

      const tryJoin = () => {
        setReady(true);
      }

    if(!interviewParticipant){
        return <InterviewNotFound />;
    }

    if(isReady){
        return <InterviewRoomPage muted={audioMuted} cameraOff={cameraMuted} participant={interviewParticipant}/>
    }

    return (
        <div className="row">
            <div className="col-8 offset-2 mt-5">
                <div className="waiting-room">
                        <h2>Preview</h2>
                        <div className="preview">
                            <div className="video-tile">
                                <video muted ref={videoPreview} autoPlay playsInline />
                                <div className="participant-name">
                                <div className="name">Myself</div>
                                <div className="status">
                                    {audioMuted && <i className="bi bi-mic-mute-fill"></i>}
                                    {cameraMuted && <i className="bi bi-camera-video-off-fill"></i>}
                                </div>
                                </div>
                            </div>
                            {loadedStream && 
                            <div className="buttons">
                                <h4>Joining settings</h4>
                                <div onClick={muteMic} className="btn btn-primary">{audioMuted? <><i className="bi bi-mic-mute-fill"></i> Muted</> : <><i className="bi bi-mic-fill"></i> Unmuted</>}</div>
                                <div onClick={muteCam} className="btn btn-primary">{cameraMuted? <><i className="bi bi-camera-video-off-fill"></i> Camera Off</> : <><i className="bi bi-camera-video-fill"></i> Camera On</>}</div>
                            </div>
                            }
                        </div>
                        <hr className="hr-line" />
                        <div className="participant-info">
                            <h4>Participating as {interviewParticipant.firstName} {interviewParticipant.lastName}</h4>
                        </div>
                        <div onClick={tryJoin} className="btn btn-lg btn-primary">Join interview room</div>
                </div>
            </div>
        </div>
    )
}

export default InterviewWaitingRoom;