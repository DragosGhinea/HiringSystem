import { Modal, Button } from 'react-bootstrap'
import jwtInterceptor from '../../../components/shared/JwtInterceptor'
import "./UsersModal.css"

const UsersModal = ({roomId, peers, streams, openModal, setOpenModal}) => {

    const muteMic = (userId) => {
        jwtInterceptor.post(`http://localhost:8081/api/v1/interview/forceAction/${roomId}/${userId}`, {
            type: "MUTE"
        });
    }

    const muteCam = (userId) => {
        jwtInterceptor.post(`http://localhost:8081/api/v1/interview/forceAction/${roomId}/${userId}`, {
            type: "CAMERA_OFF"
        });
    }

    const leave = (userId) => {
        jwtInterceptor.post(`http://localhost:8081/api/v1/interview/forceAction/${roomId}/${userId}`, {
            type: "KICK"
        });
    }

    return (
        <Modal show={openModal} size="lg">
            <Modal.Header closeButton onClick={() => setOpenModal(false)}>
                <Modal.Title>Connected users</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {peers.length===0? "You are the only one connected." : 
                peers.map((peer) => {
                    const stream = streams[peer.peerID];
                    const audioMuted = stream.mic === "off";
                    const cameraMuted = stream.camera === "off";

                    return (
                        <div key={peer.peerID} className="modal-user">
                            <span>{peer.peerFullName}</span>
                            <div className="modal-user-actions">
                                <div onClick={() => muteMic(peer.peerID)} className={"btn btn-primary "+(audioMuted?"disabled":"")}>Mute <i className="bi bi-mic-mute-fill"></i></div>
                                <div onClick={() => muteCam(peer.peerID)} className={"btn btn-primary "+(cameraMuted?"disabled":"")}>Turn camera off <i className="bi bi-camera-video-off-fill"></i></div>
                                <div onClick={() => leave(peer.peerID)} className="btn btn-primary">Kick <i className="bi bi-box-arrow-right"></i></div>
                            </div>
                        </div>
                    );
                })}
            </Modal.Body>
            <Modal.Footer>
                <Button variant="danger" onClick={() => setOpenModal(false)}>
                    Close Window
                </Button>
            </Modal.Footer>
        </Modal>
      );
}

export default UsersModal;