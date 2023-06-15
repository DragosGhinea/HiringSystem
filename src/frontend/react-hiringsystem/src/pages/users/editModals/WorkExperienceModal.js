import { Modal, Button, Form, Row, Col } from 'react-bootstrap';
import React, { useState, useEffect } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import axios from "axios";

const WorkExperienceModal = ({ show, onClose, cv }) => {
    const [editedWorkExperience, setEditedWorkExperience] = useState(
        cv?.workExperience || []
    );

    useEffect(() => {
        setEditedWorkExperience(cv?.workExperience || []);
    }, [cv]);

    const handleSave = () => {
        const cvDto = {
            skills: cv.skills,
            academicBackground: cv.academicBackground,
            workExperience: editedWorkExperience,
            projects: cv.projects
        }

        console.log(cvDto)

        axios.post(`http://localhost:8081/api/v1/candidate/edit/cv/${cv.id}`, cvDto)
            .then(() => window.location.reload()
            )
            .catch(err => {
                console.log("Error triggered")
                console.log(err)
            })

        onClose();
    };

    const handleAddWorkExperience = () => {
        setEditedWorkExperience([
            ...editedWorkExperience,
            { startDate: new Date(), endDate: new Date(), company: '', position: '' }
        ]);
    };

    const handleRemoveWorkExperience = (index) => {
        const updatedWorkExperience = [...editedWorkExperience];
        updatedWorkExperience.splice(index, 1);
        setEditedWorkExperience(updatedWorkExperience);
    };

    const handleWorkExperienceChange = (index, field, value) => {
        const updatedWorkExperience = [...editedWorkExperience];
        updatedWorkExperience[index][field] = value;
        setEditedWorkExperience(updatedWorkExperience);
    };

    return (
        <Modal show={show} onHide={onClose} size="lg">
            <Modal.Header closeButton>
                <Modal.Title style={{ fontWeight: 'bold', fontSize: '20px' }}>Edit Work Experience</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    {editedWorkExperience.map((experience, index) => (
                        <div key={index}>
                            <Row>
                                <Col>
                                    <Form.Group controlId={`formStartDate${index}`}>
                                        <Form.Label style={{ fontWeight: 'bold', fontSize: '17px', marginTop: '10px' }}>Start Date</Form.Label>
                                        <br />
                                        <DatePicker
                                            selected={experience.startDate ? new Date(experience.startDate) : null}
                                            onChange={(date) =>
                                                handleWorkExperienceChange(
                                                    index,
                                                    'startDate',
                                                    date.toISOString()
                                                )
                                            }
                                            dateFormat="dd/MM/yyyy"
                                        />
                                    </Form.Group>
                                </Col>
                                <Col>
                                    <Form.Group controlId={`formEndDate${index}`}>
                                        <Form.Label style={{ fontWeight: 'bold', fontSize: '17px', marginTop: '10px' }}>End Date</Form.Label>
                                        <br />
                                        <DatePicker
                                            selected={experience.endDate ? new Date(experience.endDate) : null}
                                            onChange={(date) =>
                                                handleWorkExperienceChange(
                                                    index,
                                                    'endDate',
                                                    date.toISOString()
                                                )
                                            }
                                            dateFormat="dd/MM/yyyy"
                                        />
                                    </Form.Group>
                                </Col>
                            </Row>
                            <Form.Group controlId={`formCompany${index}`}>
                                <Form.Label style={{ fontWeight: 'bold', fontSize: '17px', marginTop: '10px' }}>Company</Form.Label>
                                <Form.Control type="text" value={experience.company} onChange={(e) =>
                                        handleWorkExperienceChange(
                                            index,
                                            'company',
                                            e.target.value
                                        )
                                    }
                                />
                            </Form.Group>
                            <Form.Group controlId={`formPosition${index}`}>
                                <Form.Label style={{ fontWeight: 'bold', fontSize: '17px', marginTop: '10px' }}>Position</Form.Label>
                                <Form.Control
                                    type="text"
                                    value={experience.position}
                                    onChange={(e) =>
                                        handleWorkExperienceChange(
                                            index,
                                            'position',
                                            e.target.value
                                        )
                                    }
                                />
                            </Form.Group>
                            <Button variant="danger" size="sm" style={{ marginTop: '10px' }} onClick={() => handleRemoveWorkExperience(index)}>
                                Remove
                            </Button>
                            <hr />
                        </div>
                    ))}
                    <Button variant="secondary" size="sm" onClick={handleAddWorkExperience}>
                        Add Work Experience
                    </Button>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="danger" onClick={onClose}>
                    Cancel
                </Button>
                <Button variant="primary" onClick={handleSave}>
                    Save Changes
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default WorkExperienceModal;
