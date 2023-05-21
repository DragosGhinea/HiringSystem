import { useContext, useRef, useState, useEffect } from "react";
import { Col, Container, Row } from "react-bootstrap";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import JobContext from "../components/shared/JobContext";

const Create = () => {

    const {create} = useContext(JobContext);

    const title = useRef("");
    const description = useRef("");
    const salary = useRef("");
    const hoursPerWeek = useRef("");
    const startDate = useRef("");

    const [skills, setSkills] = useState([]);
    const [newSkill, setNewSkill] = useState('');
    const [offers, setOffers] = useState([]);
    const [newOffer, setNewOffer] = useState('');
    const [jobTypes, setJobTypes] = useState([]);
    const [positions, setPositions] = useState([]);
    const [jobTypeSelected, setJobTypeSelected] = useState('');
    const [positionSelected, setPositionSelected] = useState('');

    useEffect(() => {
        fetch('http://localhost:8081/api/v1/jobTypes')
            .then(response => response.json())
            .then(data => {
                setJobTypes(data);
            })
            .catch(error => {
                console.error('Error fetching jobTypes:', error);
            });
    }, []);

    useEffect(() => {
        fetch('http://localhost:8081/api/v1/positions')
            .then(response => response.json())
            .then(data => {
                setPositions(data);
            })
            .catch(error => {
                console.error('Error fetching positions:', error);
            });
    }, []);

    const handleNewSkillChange = (event) => {
        setNewSkill(event.target.value);
    };

    const handleAddSkill = () => {
        if (newSkill.trim() !== '') {
            setSkills([...skills, newSkill.trim()]);
            setNewSkill('');
        }
    };

    const handleNewOfferChange = (event) => {
        setNewOffer(event.target.value);
    };

    const handleAddOffer = () => {
        if (newOffer.trim() !== '') {
            setOffers([...offers, newOffer.trim()]);
            setNewOffer('');
        }
    };

    const createSubmit = async () => {
        let payload = {
            title: title.current.value,
            description: description.current.value,
            jobType: jobTypeSelected,
            position: positionSelected,
            salary: salary.current.value,
            hoursPerWeek: hoursPerWeek.current.value,
            startDate: startDate.current.value,
            skillsNeeded: skills,
            offers: offers
        }
        try {
            await create(payload);
        } catch (err) {
            console.log(err.config.data);
        }
    };


    return (
        <>
            <Container className="mt-2">
                <Row>
                    <Col className="col-md-8 offset-md-2">
                        <legend>Create Job Form</legend>
                        <form>
                            <Form.Group className="mb-3" controlId="formTitle">
                                <Form.Label>Title</Form.Label>
                                <Form.Control type="text" ref={title} />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="formDescription">
                                <Form.Label>Description</Form.Label>
                                <Form.Control type="text" ref={description} />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="formJobType">
                                <Form.Label>Job Type</Form.Label>
                                {jobTypes.map((jobType) => (
                                    <div key={jobType}>
                                        <Form.Check
                                            type="radio"
                                            label={jobType}
                                            name="jobType"
                                            value={jobType}
                                            checked={jobType === jobTypeSelected}
                                            onChange={(e) => setJobTypeSelected(e.target.value)}
                                        />
                                    </div>
                                ))}
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="formPosition">
                                <Form.Label>Position</Form.Label>
                                {positions.map((position) => (
                                    <div key={position}>
                                        <Form.Check
                                            type="radio"
                                            label={position}
                                            name="position"
                                            value={position}
                                            checked={position === positionSelected}
                                            onChange={(e) => setPositionSelected(e.target.value)}
                                        />
                                    </div>
                                ))}
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="formSalary">
                                <Form.Label>Salary</Form.Label>
                                <Form.Control type="number" ref={salary} />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="formHoursPerWeek">
                                <Form.Label>Hours Per Week</Form.Label>
                                <Form.Control type="number" ref={hoursPerWeek} />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="formStartDate">
                                <Form.Label>Start Date</Form.Label>
                                <Form.Control type="date" ref={startDate} />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="formSkillsNeeded">
                                <Form.Label>Skills needed</Form.Label>
                                <div>
                                    <span>{`${skills.join(', ')}`}</span>
                                </div>
                                <Form.Control
                                    type="text"
                                    value={newSkill}
                                    onChange={handleNewSkillChange}
                                    placeholder="Enter a skill"
                                />
                                <Button variant="primary" onClick={handleAddSkill}>
                                    Add Skill
                                </Button>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="formOffers">
                                <Form.Label>Offers</Form.Label>
                                <div>
                                    <span>{`${offers.join(', ')}`}</span>
                                </div>
                                <Form.Control
                                    type="text"
                                    value={newOffer}
                                    onChange={handleNewOfferChange}
                                    placeholder="Enter an offer"
                                />
                                <Button variant="primary" onClick={handleAddOffer}>
                                    Add Offer
                                </Button>
                            </Form.Group>
                            <Button variant="primary" type="button" onClick={createSubmit}>
                                Create
                            </Button>
                        </form>
                    </Col>
                </Row>
            </Container>
        </>
    );
};

export default Create;
