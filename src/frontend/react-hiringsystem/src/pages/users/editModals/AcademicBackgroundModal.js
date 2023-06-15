import { Modal, Button, Form, Row, Col } from 'react-bootstrap';
import React, { useState, useEffect } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import axios from "axios";

const AcademicBackgroundModal = ({ show, onClose, cv }) => {
    const [editedAcademicBackground, setEditedAcademicBackground] = useState(
        cv?.academicBackground || []
    );

    useEffect(() => {
        setEditedAcademicBackground(cv?.academicBackground || []);
    }, [cv]);

    const handleSave = () => {
        const cvDto = {
            skills: cv.skills,
            academicBackground: editedAcademicBackground,
            workExperience: cv.workExperience,
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
    };

    const handleAddAcademicBackground = () => {
        setEditedAcademicBackground([
            ...editedAcademicBackground,
            { startDate: '', endDate: '', institution: '', specialization: '' }
        ]);
    };

    const handleRemoveAcademicBackground = (index) => {
        const updatedAcademicBackground = [...editedAcademicBackground];
        updatedAcademicBackground.splice(index, 1);
        setEditedAcademicBackground(updatedAcademicBackground);
    };

    const handleAcademicBackgroundChange = (index, field, value) => {
        const updatedAcademicBackground = [...editedAcademicBackground];
        updatedAcademicBackground[index][field] = value;
        setEditedAcademicBackground(updatedAcademicBackground);
    };

    return (
        <Modal show={show} onHide={onClose} size="lg">
            <Modal.Header closeButton>
                <Modal.Title style={{ fontWeight: 'bold', fontSize: '20px' }}>Edit Academic Background</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    {editedAcademicBackground.map((background, index) => (
                        <div key={index}>
                            <Row>
                                <Col>
                                    <Form.Group controlId={`formStartDate${index}`}>
                                        <Form.Label style={{ fontWeight: 'bold', fontSize: '17px' , marginTop: '10px' }}>Start Date</Form.Label>
                                        <br />
                                        <DatePicker
                                            selected={background.startDate ? new Date(background.startDate) : null}
                                            onChange={(date) =>
                                                handleAcademicBackgroundChange(
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
                                        <Form.Label style={{ fontWeight: 'bold', fontSize: '17px' , marginTop: '10px' }}>End Date</Form.Label>
                                        <br />
                                        <DatePicker
                                            selected={background.endDate ? new Date(background.endDate) : null}
                                            onChange={(date) =>
                                                handleAcademicBackgroundChange(
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
                            <Form.Group controlId={`formInstitution${index}`}>
                                <Form.Label style={{ fontWeight: 'bold', fontSize: '17px' , marginTop: '10px' }}>Institution</Form.Label>
                                <Form.Control
                                    type="text"
                                    value={background.institution}
                                    onChange={(e) =>
                                        handleAcademicBackgroundChange(
                                            index,
                                            'institution',
                                            e.target.value
                                        )
                                    }
                                />
                            </Form.Group>
                            <Form.Group controlId={`formSpecialization${index}`}>
                                <Form.Label style={{ fontWeight: 'bold', fontSize: '17px' , marginTop: '10px' }}>Specialization</Form.Label>
                                <Form.Control
                                    type="text"
                                    value={background.specialization}
                                    onChange={(e) =>
                                        handleAcademicBackgroundChange(
                                            index,
                                            'specialization',
                                            e.target.value
                                        )
                                    }
                                />
                            </Form.Group>
                            <Button
                                variant="danger"
                                size="sm"
                                style={{ marginTop: '10px', marginRight: '10px' }}
                                onClick={() => handleRemoveAcademicBackground(index)}
                            >
                                Remove
                            </Button>
                            <hr />
                        </div>
                    ))}
                    <Button variant="secondary" size="sm" onClick={handleAddAcademicBackground}>
                        Add Academic Background
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

export default AcademicBackgroundModal;
