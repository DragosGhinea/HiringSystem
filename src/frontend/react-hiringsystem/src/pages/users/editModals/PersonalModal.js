import { Modal, Button, Form, Row, Col } from 'react-bootstrap';
import React, { useState, useEffect } from 'react';
import axios from "axios";

const PersonalModal = ({ show, onClose, user, cv }) => {
    const [editedUser, setEditedUser] = useState({
        firstName: user.firstName,
        lastName: user.lastName
    });

    const [editedLinks, setEditedLinks] = useState({
        githubProfileLink: user.githubProfileLink,
        linkedInProfileLink: user.linkedInProfileLink
    });

    const [editedPhoneNumbers, setEditedPhoneNumbers] = useState(
        user.phoneNumberList || []
    );

    const [editedSkills, setEditedSkills] = useState([]);

    useEffect(() => {
        console.log(cv);
        setEditedSkills(cv?.skills ? [...cv.skills] : []);
    }, [cv]);

    const handleSave = () => {
        const candidateUserDto = {
            firstName: editedUser.firstName,
            lastName: editedUser.lastName,
            primaryEmail: user.primaryEmail,
            mailList: user.mailList,
            phoneNumberList: editedPhoneNumbers,
            birthDate: user.birthDate,
            password: user.password,
            githubProfileLink: editedLinks.githubProfileLink,
            linkedInProfileLink: editedLinks.linkedInProfileLink
        };

        const cvDto = {
            skills: editedSkills,
            academicBackground: cv.academicBackground,
            workExperience: cv.workExperience,
            projects: cv.projects
        }

        console.log(candidateUserDto)

        console.log(cvDto)

        axios.post(`http://localhost:8081/api/v1/candidate/edit/${user.id}`, candidateUserDto)
            .then(() =>
                axios.post(`http://localhost:8081/api/v1/candidate/edit/cv/${user.id}`, cvDto)
                    .then(() => window.location.reload()
                    )
            )
            .catch(err => {
                console.log("Error triggered")
                console.log(err)
            })

        onClose();
    };

    const handleAddPhoneNumber = () => {
        setEditedPhoneNumbers([...editedPhoneNumbers, '']);
    };

    const handleRemovePhoneNumber = (index) => {
        const updatedPhoneNumbers = [...editedPhoneNumbers];
        updatedPhoneNumbers.splice(index, 1);
        setEditedPhoneNumbers(updatedPhoneNumbers);
    };

    const handlePhoneNumberChange = (index, value) => {
        const updatedPhoneNumbers = [...editedPhoneNumbers];
        updatedPhoneNumbers[index] = value;
        setEditedPhoneNumbers(updatedPhoneNumbers);
    };

    const handleAddSkill = () => {
        setEditedSkills([...editedSkills, '']);
    };

    const handleRemoveSkill = (index) => {
        const updatedSkills = [...editedSkills];
        updatedSkills.splice(index, 1);
        setEditedSkills(updatedSkills);
    };

    const handleSkillChange = (index, value) => {
        const updatedSkills = [...editedSkills];
        updatedSkills[index] = value;
        setEditedSkills(updatedSkills);
    };

    return (
        <Modal show={show} onHide={onClose} size="lg">
            <Modal.Header closeButton>
                <Modal.Title>Edit personal details</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <h4>Names</h4>
                    <Form.Group controlId="formFirstName">
                        <Form.Label style={{marginTop: '10px', fontSize: '18px', fontWeight: 'bold'}}>First Name</Form.Label>
                        <Form.Control
                            type="text"
                            value={editedUser.firstName || user.firstName}
                            onChange={(e) =>
                                setEditedUser({ ...editedUser, firstName: e.target.value })
                            }
                        />
                    </Form.Group>
                    <Form.Group controlId="formLastName">
                        <Form.Label style={{marginTop: '15px', fontSize: '18px', fontWeight: 'bold'}}>Last Name</Form.Label>
                        <Form.Control
                            type="text"
                            value={editedUser.lastName || user.lastName}
                            onChange={(e) =>
                                setEditedUser({ ...editedUser, lastName: e.target.value })
                            }
                        />
                    </Form.Group>
                    <hr />
                    <h4>Links</h4>
                    <Form.Group controlId="formGithub">
                        <Form.Label  style={{marginTop: '10px', fontSize: '18px', fontWeight: 'bold'}}>Github Profile</Form.Label>
                        <Form.Control
                            type="text"
                            value={editedLinks.githubProfileLink || user.githubProfileLink}
                            onChange={(e) =>
                                setEditedLinks({
                                    ...editedLinks,
                                    githubProfileLink: e.target.value
                                })
                            }
                        />
                    </Form.Group>
                    <Form.Group controlId="formLinkedIn">
                        <Form.Label  style={{marginTop: '10px', fontSize: '18px', fontWeight: 'bold'}}>LinkedIn Profile</Form.Label>
                        <Form.Control
                            type="text"
                            value={
                                editedLinks.linkedInProfileLink || user.linkedInProfileLink
                            }
                            onChange={(e) =>
                                setEditedLinks({
                                    ...editedLinks,
                                    linkedInProfileLink: e.target.value
                                })
                            }
                        />
                    </Form.Group>
                    <hr />
                    <h4>Phone Numbers</h4>
                    {editedPhoneNumbers.map((phoneNumber, index) => (
                        <Row key={index} className="mb-2">
                            <Col sm={10}>
                                <Form.Control
                                    type="text"
                                    value={phoneNumber}
                                    onChange={(e) =>
                                        handlePhoneNumberChange(index, e.target.value)
                                    }
                                />
                            </Col>
                            <Col sm={2}>
                                <Button
                                    variant="danger"
                                    size="sm"
                                    onClick={() => handleRemovePhoneNumber(index)}
                                >
                                    Remove
                                </Button>
                            </Col>
                        </Row>
                    ))}
                    <Button variant="secondary" size="sm" onClick={handleAddPhoneNumber}>
                        Add Phone Number
                    </Button>
                    <hr />
                    <h4>Skills</h4>
                    {editedSkills.map((skill, index) => (
                        <Row key={index} className="mb-2">
                            <Col sm={10}>
                                <Form.Control
                                    type="text"
                                    value={skill}
                                    onChange={(e) => handleSkillChange(index, e.target.value)}
                                />
                            </Col>
                            <Col sm={2}>
                                <Button
                                    variant="danger"
                                    size="sm"
                                    onClick={() => handleRemoveSkill(index)}
                                >
                                    Remove
                                </Button>
                            </Col>
                        </Row>
                    ))}
                    <div className="mt-3">
                        <Button variant="secondary" size="sm" onClick={handleAddSkill}>
                            Add Skill
                        </Button>
                    </div>
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

export default PersonalModal;
