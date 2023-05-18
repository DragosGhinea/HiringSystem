import { useContext, useRef, useState } from "react";
import { Col, Container, Row } from "react-bootstrap";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import JobContext from "../components/shared/JobContext";

const Create = () => {
    const title = useRef("");
    const description = useRef("");
    const jobType = useRef("");
    const position = useRef("");
    const salary = useRef("");
    const hoursPerWeek = useRef("");
    const startDate = useRef("");
    const {create} = useContext(JobContext);

    const [skills, setSkills] = useState([]);
    const [newSkill, setNewSkill] = useState('');
    const [offers, setOffers] = useState([]);
    const [newOffer, setNewOffer] = useState('');

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
            jobType: jobType.current.value,
            position: position.current.value,
            salary: salary.current.value,
            hoursPerWeek: salary.current.value,
            startDate: startDate.current.value
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
                                <Form.Label>Type</Form.Label>
                                <Form.Control type="text" ref={jobType} />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="formPosition">
                                <Form.Label>Position</Form.Label>
                                <Form.Control type="text" ref={position} />
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
