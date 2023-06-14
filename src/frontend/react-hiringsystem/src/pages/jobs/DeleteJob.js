import { useContext } from "react";
import JobApplicationContext from "../../components/shared/JobContext";
import Button from "react-bootstrap/Button";
import {useParams} from "react-router-dom";

// THIS PAGE IS FOR TEST PURPOSES ONLY
const DeleteJob = () => {

    const { id } = useParams();

    const {deleteJob} = useContext(JobApplicationContext);

    const deleteSubmit = async () => {
        try {
            await deleteJob(id);
        } catch (err) {
            console.log(err);
        }
    };

    return (
        <>
            <Button variant="primary" type="button" onClick={deleteSubmit}>
                Delete
            </Button>
        </>
    );
};

export default DeleteJob;
