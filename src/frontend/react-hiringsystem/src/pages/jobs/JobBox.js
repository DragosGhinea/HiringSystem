import { useState } from 'react'
import { useNavigate } from 'react-router-dom'

const JobBox = ({job, index}) => {
    const [title, setTitle] = useState(job.title);
    const [position, setPosition] = useState(job.position);
    const [description, setDescription] = useState(job.description);
    const navigate = useNavigate();

    const goToJob = () => {
        navigate(`/jobs/job/${job.id}`);
    };

    return (
        <div className="job-box">
            <h1 className="job-title">{title}</h1>
            <div className="job-box-info">
                <span className="job-box-label">Position:</span>
                <span className="job-box-value">{position}</span>
            </div>
            <span className="job-description">{description}</span>
            <div className="job-box-action">
                <button className="btn btn-primary" onClick={goToJob}>
                    View job
                </button>
            </div>
        </div>
    );
};

export default JobBox;