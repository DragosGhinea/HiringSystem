import { Modal, Button } from 'react-bootstrap'

const CloseInterviewModal = ({closeMethod, setOpenModal}) => {

    return (
        <Modal show={true}>
            <Modal.Header closeButton onClick={closeMethod}>
                <Modal.Title>Are you sure?</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                Are you sure you want to close this interview room, kicking everyone out?
            </Modal.Body>
            <Modal.Footer>
                <Button variant="danger" onClick={closeMethod}>
                    Close Interview Room
                </Button>
                <Button variant="secondary" onClick={() => setOpenModal(false)}>
                    Cancel
                </Button>
            </Modal.Footer>
        </Modal>
      );
}

export default CloseInterviewModal;