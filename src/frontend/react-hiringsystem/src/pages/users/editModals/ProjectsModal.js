import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Modal, Button, Row } from 'react-bootstrap';

function ProjectsModal({ show, onClose, cv }) {
    const [editedProjects, setEditedProjects] = useState(cv?.projects || []);

    useEffect(() => {
        setEditedProjects(cv?.projects || []);
    }, [cv]);

    const handleSave = () => {
        const cvDto = {
            skills: cv.skills,
            academicBackground: cv.academicBackground,
            workExperience: cv.workExperience,
            projects: editedProjects,
        };

        console.log(cvDto);

        axios
            .post(`http://localhost:8081/api/v1/candidate/edit/cv/${cv.id}`, cvDto)
            .then(() => window.location.reload())
            .catch((err) => {
                console.log('Error triggered');
                console.log(err);
            });

        onClose();
    };

    const handleAddProject = () => {
        setEditedProjects([...editedProjects, { title: '', description: '' }]);
    };

    const handleRemoveProject = (index) => {
        const updatedProjects = [...editedProjects];
        updatedProjects.splice(index, 1);
        setEditedProjects(updatedProjects);
    };

    const handleProjectChange = (index, field, value) => {
        const updatedProjects = [...editedProjects];
        updatedProjects[index][field] = value;
        setEditedProjects(updatedProjects);
    };

    return (
        <Modal show={show} onHide={onClose} size="lg">
            <Modal.Header closeButton>
                <Modal.Title>Edit Projects</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {editedProjects.map((project, index) => (
                    <div key={index} className="form-group">
                        <label style={{ fontWeight: 'bold', fontSize: '17px', marginTop: '10px' }}>Title</label>
                        <input
                            type="text"
                            className="form-control"
                            value={project.title}
                            onChange={(e) => handleProjectChange(index, 'title', e.target.value)}
                        />
                        <label style={{ fontWeight: 'bold', fontSize: '17px', marginTop: '10px' }}>Description</label>
                        <textarea
                            className="form-control"
                            rows={5}
                            value={project.description}
                            onChange={(e) => handleProjectChange(index, 'description', e.target.value)}
                        />
                        <Button
                            variant="danger"
                            onClick={() => handleRemoveProject(index)}
                            className="mt-3"
                        >
                            Remove Project
                        </Button>
                        <hr /> {/* Add a horizontal line after each project */}
                    </div>
                ))}
                <Button variant="secondary" onClick={handleAddProject} className="mt-3">
                    Add Project
                </Button>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="danger" onClick={onClose}>
                    Close
                </Button>
                <Button variant="primary" onClick={handleSave}>
                    Save Changes
                </Button>
            </Modal.Footer>
        </Modal>
    );
}

export default ProjectsModal;
