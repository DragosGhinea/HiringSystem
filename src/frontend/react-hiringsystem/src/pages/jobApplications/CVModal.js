import { Modal, Button } from 'react-bootstrap'

const CVModal = ({cv, setCV}) => {
    if(!cv)
        return null;

    return (
        <Modal show={true} size="lg">
            <Modal.Header closeButton onClick={() => setCV(null)}>
                <Modal.Title>CV of {cv.user.firstName} {cv.user.lastName}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                
            </Modal.Body>
            <Modal.Footer>
                <Button variant="danger" onClick={() => setCV(null)}>
                    Close Window
                </Button>
            </Modal.Footer>
        </Modal>
      );
}

export default CVModal;