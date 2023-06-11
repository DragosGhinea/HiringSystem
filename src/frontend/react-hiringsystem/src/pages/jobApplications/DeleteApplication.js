import { useContext } from "react";
import JobApplicationContext from "../../components/shared/JobApplicationContext";
import Button from "react-bootstrap/Button";
import {useParams} from "react-router-dom";

// THIS PAGE IS FOR TEST PURPOSES ONLY
const CreateDeleteApplication = () => {

    const { jobApplicationId } = useParams();

    const {deleteApplication} = useContext(JobApplicationContext);

    const deleteSubmit = async () => {
        try {
            await deleteApplication(jobApplicationId);
        } catch (err) {
            console.log(err);
        }
    };

    return (
        <>
            <Button variant="danger" type="button" onClick={deleteSubmit}>
                Delete
            </Button>
        </>
    );
};

export default CreateDeleteApplication;
