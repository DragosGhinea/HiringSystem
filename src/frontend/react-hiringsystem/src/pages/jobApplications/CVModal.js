import { Modal, Button } from 'react-bootstrap';
import React from "react";

const CVModal = ({ cv, setCV }) => {
    if (!cv) {
        return null;
    }

    function formatDate(date) {
        if (!date) {
            return 'N/A';
        }

        const options = { day: 'numeric', month: 'long', year: 'numeric' };
        return new Date(date).toLocaleDateString(undefined, options);
    }

    return (
        <Modal show={true} size="lg">
            <Modal.Header closeButton onClick={() => setCV(null)}>
                <Modal.Title>CV of {cv.user.firstName} {cv.user.lastName}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div style={{ display: 'flex', alignItems: 'center', marginBottom: '1em' }}>
                    <h4 style={{ marginRight: '1em' }}>Email:</h4>
                    <p style={{ fontSize: '22px', marginTop: '8px' }}>{cv.user.primaryEmail}</p>
                </div>
                {cv.user.githubProfileLink && (
                    <div style={{ display: 'flex', alignItems: 'center', marginBottom: '1em' }}>
                        <h4 style={{ marginRight: '1em' }}>Github profile link:</h4>
                        <a
                            href={cv.user.githubProfileLink}
                            target="_blank"
                            rel="noopener noreferrer"
                            style={{ fontSize: '22px', marginTop: '-9px', verticalAlign: 'middle' }}
                        >
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="feather feather-github mr-2 icon-inline">
                                <path d="M9 19c-5 1.5-5-2.5-7-3m14 6v-3.87a3.37 3.37 0 0 0-.94-2.61c3.14-.35 6.44-1.54 6.44-7A5.44 5.44 0 0 0 20 4.77 5.07 5.07 0 0 0 19.91 1S18.73.65 16 2.48a13.38 13.38 0 0 0-7 0C6.27.65 5.09 1 5.09 1A5.07 5.07 0 0 0 5 4.77a5.44 5.44 0 0 0-1.5 3.78c0 5.42 3.3 6.61 6.44 7A3.37 3.37 0 0 0 9 18.13V22"></path>
                            </svg>
                            {cv.user.githubProfileLink}
                        </a>
                    </div>
                )}
                {cv.user.linkedInProfileLink && (
                    <div style={{ display: 'flex', alignItems: 'center', marginBottom: '1em' }}>
                        <h4 style={{ marginRight: '1em' }}>LinkedIn profile link:</h4>
                        <a
                            href={cv.user.linkedInProfileLink}
                            target="_blank"
                            rel="noopener noreferrer"
                            style={{ fontSize: '22px', marginTop: '-9px', verticalAlign: 'middle' }}
                        >
                            <svg xmlns="http://www.w3.org/2000/svg" width="23" height="23" fill="currentColor" className="bi bi-linkedin" viewBox="0 0 16 16">
                                <path d="M0 1.146C0 .513.526 0 1.175 0h13.65C15.474 0 16 .513 16 1.146v13.708c0 .633-.526 1.146-1.175 1.146H1.175C.526 16 0 15.487 0 14.854V1.146zm4.943 12.248V6.169H2.542v7.225h2.401zm-1.2-8.212c.837 0 1.358-.554 1.358-1.248-.015-.709-.52-1.248-1.342-1.248-.822 0-1.359.54-1.359 1.248 0 .694.521 1.248 1.327 1.248h.016zm4.908 8.212V9.359c0-.216.016-.432.08-.586.173-.431.568-.878 1.232-.878.869 0 1.216.662 1.216 1.634v3.865h2.401V9.25c0-2.22-1.184-3.252-2.764-3.252-1.274 0-1.845.7-2.165 1.193v.025h-.016a5.54 5.54 0 0 1 .016-.025V6.169h-2.4c.03.678 0 7.225 0 7.225h2.4z"></path>
                            </svg>
                            {cv.user.linkedInProfileLink}
                        </a>
                    </div>
                )}
                {cv.academicBackground && (
                    <div style={{ display: 'flex', alignItems: 'center', marginBottom: '1em' }}>
                        <h4 style={{ marginRight: '1em' }}>Academic Background:</h4>
                    </div>
                )}
                {cv.academicBackground && (
                    <ul style={{ fontSize: '22px', marginTop: '-3px', marginLeft: '1em', marginBottom: '1em' }}>
                        {cv.academicBackground.map((academic, index) => (
                            <li key={index} style={{ marginBottom: '0.7em' }}>
                                <strong>{academic.institution}</strong>
                                <br />
                                <em style={{ marginLeft: '15px', fontSize: '22px' }}> -- {academic.specialization} -- {academic.level} </em>
                                <br />
                                <div style={{ marginLeft: '20px', fontSize: '22px' }}>{formatDate(academic.startDate)} - {formatDate(academic.endDate)}</div>
                            </li>
                        ))}
                    </ul>
                )}
                {cv.workExperience && (
                    <div style={{ display: 'flex', alignItems: 'center', marginBottom: '1em' }}>
                        <h4 style={{ marginRight: '1em' }}>Experience:</h4>
                    </div>
                )}
                {cv.workExperience && (
                    <ul style={{ fontSize: '22px', marginTop: '-10px', marginLeft: '1em', marginBottom: '1em' }}>
                        {cv.workExperience.map((experience, index) => (
                            <li key={index} style={{ marginBottom: '0.4em' }}>
                                <strong>{experience.company} -</strong> {experience.position}
                            </li>
                        ))}
                    </ul>
                )}
                {cv.skills && (
                    <div style={{ marginBottom: '1em' }}>
                        <h4 style={{ marginBottom: '0.5em' }}>Skills:</h4>
                        <ul style={{ fontSize: '22px', marginTop: '-4', marginLeft: '1em', marginBottom: '1em' }}>
                            {cv.skills.map((skill, index) => (
                                <li key={index} style={{ marginBottom: '0.1em' }}>{skill}</li>
                            ))}
                        </ul>
                    </div>
                )}
                {cv.projects && (
                    <div style={{ display: 'flex', alignItems: 'center', marginBottom: '1em' }}>
                        <h4 style={{ marginRight: '1em' }}>Projects:</h4>
                    </div>
                )}
                {cv.projects && (
                    <ul style={{ fontSize: '22px', marginTop: '5px', marginLeft: '1em', marginBottom: '1em' }}>
                        {cv.projects.map((project, index) => (
                            <li key={index} style={{ marginBottom: '1em' }}>
                                <div style={{ fontWeight: 'bold', fontSize: '22px' }}>{project.title}</div>
                                <div style={{ marginLeft: '10px', fontSize: '21px', textAlign: 'justify', marginRight: '15px' }}>{project.description}</div>
                            </li>
                        ))}
                    </ul>
                )}
            </Modal.Body>
            <Modal.Footer>
                <Button variant="danger" onClick={() => setCV(null)}>
                    Close Window
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default CVModal;
