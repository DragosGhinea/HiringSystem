import { useContext } from "react";
import JobApplicationContext from "../../components/shared/JobApplicationContext";
import Button from "react-bootstrap/Button";
import {useParams} from "react-router-dom";

// THIS PAGE IS FOR TEST PURPOSES ONLY
const CreateApplication = () => {

    const { jobId } = useParams();

    const {createApplication} = useContext(JobApplicationContext);

    const createSubmit = async () => {
        try {
            await createApplication(jobId);
        } catch (err) {
            console.log(err);
        }
    };

    return (
        <>
            <Button variant="primary" type="button" onClick={createSubmit}>
                Create
            </Button>
        </>
    );
};

export default CreateApplication;
