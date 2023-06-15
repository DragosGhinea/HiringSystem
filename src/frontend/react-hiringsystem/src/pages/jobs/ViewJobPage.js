import { useContext, useState, useEffect } from "react";
import { Col, Container, Row } from "react-bootstrap";
import JobContext from "../../components/shared/JobContext";
import {useNavigate, useParams} from 'react-router-dom';
import jwtInterceptor from "../../components/shared/JwtInterceptor";
import AuthContext from "../../components/shared/AuthContext";

const ViewJobPage = () => {
    const { id } = useParams();

    const { user } = useContext(AuthContext);

    const navigate = useNavigate();

    const [job, setJob] = useState(null);

    const [isApplied, setIsApplied] = useState(false);

    const {getJob} = useContext(JobContext);

    useEffect(() => {
        const fetchJob = async () => {
            const job = await getJob(id);
            setJob(job)
        };
        fetchJob();
    }, [id]);

    useEffect(() => {
        const checkApplicationStatus = async () => {
            try {
                const response = await jwtInterceptor.get(`http://localhost:8081/api/v1/application/check/${id}`);
                setIsApplied(response.data);
            } catch (err) {
                console.error('Error checking application status!');
                console.log(err);
            }
        };

        if (user && id != null) {
            checkApplicationStatus();
        }
    }, [id]);

    const handleApplyNow = async () => {
        if(id === null || user === null)
            return;

        try {

            await jwtInterceptor.post(`http://localhost:8081/api/v1/application/apply/${id}`)

            setIsApplied(true);
        } catch (err) {
            console.error('Error applying to job!')
            console.log(err);
        }
    };

    const handleViewApplications = () => {
        navigate(`/jobs/applications/${id}`);
    };

    return (
        <>
            <Container className="mt-2" style={{ backgroundColor: '#F0F0F0', color: '#333' }}>
                <Row>
                    <Col className="col-md-10 offset-md-1">
                        <div>
                            {job && (
                                <>
                                    {(user && (user.userType === "interviewer" || user.userType === "manager")) &&
                                     <div className="text-end">
                                        <button className="btn btn-secondary btn-lg mt-4" onClick={handleViewApplications}>
                                            View All Applications
                                        </button>
                                    </div>
                                    }
                                    <h1 className="mb-4 mt-3 fs-9 fw-bold text-center">{job.title}</h1>
                                    <div className="d-flex justify-content-center my-4 mx-4">
                                        <p className="me-5" style={{ fontSize: '19px', marginTop: '10px' }}>
                                            <strong>Position:</strong> {job.position}
                                        </p>
                                        <p style={{ fontSize: '19px', marginTop: '10px' }}>
                                            <strong>Job Type:</strong> {job.jobType}
                                        </p>
                                    </div>
                                    <div className="mb-5">
                                        <p className="fw-bold fs-5" style={{ marginTop: '30px' }}>A few words about this job and our team:</p>
                                        <p className="text-start ms-5" style={{ fontSize: '18px', marginTop: '-10px' }}>{job.description}</p>
                                    </div>
                                    <div className="row mt-5">
                                        <div className="col-md-5 mb-4">
                                            <div className="card h-100" style={{ backgroundColor: '#FFFFFF' }}>
                                                <div className="card-body">
                                                    <p className="fw-normal fs-5" style={{ marginBottom: '2px' }}>
                                                        <strong>Skills needed:</strong>
                                                    </p>
                                                    <ul className="ms-7">
                                                        {job.skillsNeeded &&
                                                            job.skillsNeeded.map((skill) => (
                                                                <li key={skill} style={{ fontSize: '18px', marginBottom: '4px', listStyleType: 'none' }}>
                                                                    <span className="me-2">&#x2713;</span>
                                                                    {skill}
                                                                </li>
                                                            ))}
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                        <div className="col-md-5 offset-md-2 mb-4">
                                            <div className="card h-100" style={{ backgroundColor: '#FFFFFF' }}>
                                                <div className="card-body">
                                                    <p className="fw-normal fs-5" style={{ marginBottom: '2px' }}>
                                                        <strong>Our Offers:</strong>
                                                    </p>
                                                    <ul className="ms-7">
                                                        {job.offers &&
                                                            job.offers.map((offer) => (
                                                                <li key={offer} style={{ fontSize: '18px', marginBottom: '4px', listStyleType: 'none' }}>
                                                                    <span className="me-2">&#x2713;</span>
                                                                    {offer}
                                                                </li>
                                                            ))}
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="row mt-4">
                                        <div className="col-md-6">
                                            <p className="fw-normal fs-5" style={{ marginBottom: '2px' }}>
                                                <strong>Some other important details:</strong>
                                            </p>
                                            <div className="mt-3 ms-4" style={{ fontSize: '17px' }}>
                                                {job.startDate && (
                                                    <p>
                                                        <i className="bi bi-calendar3"></i>&nbsp; <strong>Start date:</strong> {job.startDate}
                                                    </p>
                                                )}
                                                {job.salary && (
                                                    <p>
                                                        <i className="bi bi-currency-dollar"></i>&nbsp; <strong>Expected salary:</strong> {job.salary}
                                                    </p>
                                                )}
                                                {job.hoursPerWeek && (
                                                    <p>
                                                        <i className="bi bi-clock"></i>&nbsp; <strong>Hours per week:</strong> {job.hoursPerWeek}
                                                    </p>
                                                )}
                                            </div>
                                        </div>
                                        {user && user.userType === "candidate" &&
                                            <div className="col-md-6 d-flex justify-content-end align-items-center" style={{ marginTop: '80px' }}>
                                                {!isApplied ? (
                                                    <button className="btn btn-primary" onClick={handleApplyNow}>Apply Now</button>
                                                ) : (
                                                    <p style={{fontSize: '19px', fontStyle: 'italic'}}>You have applied to this job.</p>
                                                )}
                                            </div>
                                        }
                                    </div>
                                </>
                            )}
                        </div>
                    </Col>
                </Row>
            </Container>
        </>
    );
};

export default ViewJobPage;
