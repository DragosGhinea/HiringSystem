import React, {useState, useEffect} from 'react';
import "../css/candidateProfile.css"
import {useParams} from "react-router-dom";
import jwtInterceptor from '../../components/shared/JwtInterceptor';
import PersonalModal from "./editModals/PersonalModal";
import AcademicBackgroundModal from "./editModals/AcademicBackgroundModal";
import WorkExperienceModal from "./editModals/WorkExperienceModal";
import ProjectsModal from "./editModals/ProjectsModal";

function CandidateProfile({userId}) {

    const id = userId;

    const [user, setUser] = useState(null);

    const [cv, setCv] = useState(null);

    const [personalModal, setPersonalModal] = useState(false);
    const [academicBackgroundModal, setAcademicBackgroundModal] = useState(false);
    const [workExperienceModal, setWorkExperienceModal] = useState(false);
    const [projectsModal, setProjectsModal] = useState(false);

    const handleOpenPersonalModal = () => setPersonalModal(true);
    const handleClosePersonalModal = () => setPersonalModal(false);
    const handleOpenAcademicBackgroundModal = () => setAcademicBackgroundModal(true);
    const handleCloseAcademicBackgroundModal = () => setAcademicBackgroundModal(false);
    const handleOpenWorkExperienceModal = () => setWorkExperienceModal(true);
    const handleCloseWorkExperienceModal = () => setWorkExperienceModal(false);
    const handleOpenProjectsModal = () => setProjectsModal(true);
    const handleCloseProjectsModal = () => setProjectsModal(false);

    useEffect(() => {
        jwtInterceptor
            .get(`http://localhost:8081/api/v1/candidate/profile/${id}`)
            .then(function (response) {
                console.log(response);
                setUser(response.data);
            })
            .catch(function (error) {
                console.log(error);
            });
    }, [id]);

    useEffect(() => {
        if (user != null) {
            jwtInterceptor
                .get(`http://localhost:8081/api/v1/candidate/get/cv/${user.id}`)
                .then(function (response) {
                    console.log(response);
                    setCv(response.data);
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    }, [user]);

    function formatDate(date) {
        if (!date) {
            return 'N/A';
        }

        const options = { day: 'numeric', month: 'long', year: 'numeric' };
        return new Date(date).toLocaleDateString(undefined, options);
    }

    return (user &&
        <div className="containerCandidate">
            <div className="background"></div>
            <div className="main-body candidate-info">
                        <div className="card" id="personal">
                            <div className="card-body">
                                <div className="d-flex flex-column align-items-center text-center">
                                    <img src={require("../css/images/candidate.png")} alt="Candidate" width="150"/>
                                    <div className="mt-3 personalDataPanel">
                                        <h4>{user.firstName + ' ' + user.lastName}</h4>
                                        <p className="text-secondary mb-1">Candidate</p>
                                        <div>
                                            <div className="card" id="contact">
                                                <div className="card-body">
                                                    <div className="row">
                                                        <div className="col-sm-3">
                                                            <h6 className="mb-0">Github</h6>
                                                        </div>
                                                        <div className="col-sm-9 text-secondary personalDataPanel">
                                                            <div className="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                                                                <h6 className="mb-0">
                                                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="feather feather-github mr-2 icon-inline">
                                                                        <path d="M9 19c-5 1.5-5-2.5-7-3m14 6v-3.87a3.37 3.37 0 0 0-.94-2.61c3.14-.35 6.44-1.54 6.44-7A5.44 5.44 0 0 0 20 4.77 5.07 5.07 0 0 0 19.91 1S18.73.65 16 2.48a13.38 13.38 0 0 0-7 0C6.27.65 5.09 1 5.09 1A5.07 5.07 0 0 0 5 4.77a5.44 5.44 0 0 0-1.5 3.78c0 5.42 3.3 6.61 6.44 7A3.37 3.37 0 0 0 9 18.13V22"></path>
                                                                    </svg>
                                                                </h6>
                                                                <span className="text-secondary">{user.githubProfileLink}</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <hr></hr>
                                                        <div className="row">
                                                            <div className="col-sm-3">
                                                                <h6 className="mb-0">LinkedIn</h6>
                                                            </div>
                                                            <div className="col-sm-9 text-secondary personalDataPanel">
                                                                <div className="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                                                                    <h6 className="mb-0">
                                                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-linkedin" viewBox="0 0 16 16">
                                                                            <path d="M0 1.146C0 .513.526 0 1.175 0h13.65C15.474 0 16 .513 16 1.146v13.708c0 .633-.526 1.146-1.175 1.146H1.175C.526 16 0 15.487 0 14.854V1.146zm4.943 12.248V6.169H2.542v7.225h2.401zm-1.2-8.212c.837 0 1.358-.554 1.358-1.248-.015-.709-.52-1.248-1.342-1.248-.822 0-1.359.54-1.359 1.248 0 .694.521 1.248 1.327 1.248h.016zm4.908 8.212V9.359c0-.216.016-.432.08-.586.173-.431.568-.878 1.232-.878.869 0 1.216.662 1.216 1.634v3.865h2.401V9.25c0-2.22-1.184-3.252-2.764-3.252-1.274 0-1.845.7-2.165 1.193v.025h-.016a5.54 5.54 0 0 1 .016-.025V6.169h-2.4c.03.678 0 7.225 0 7.225h2.4z"></path>
                                                                        </svg>
                                                                    </h6>
                                                                <span className="text-secondary">{user.linkedInProfileLink}</span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                <hr></hr>
                                                    <div className="row">
                                                        <div className="col-sm-3">
                                                            <h6 className="mb-0">Email</h6>
                                                        </div>
                                                        <div className="col-sm-9 text-secondary">
                                                            <ul className="style-type-none">
                                                                {user.mailList?.map((mail, index) => (
                                                                    <li key={index}>{mail}</li>
                                                                ))}
                                                            </ul>
                                                        </div>
                                                    </div>
                                                <hr></hr>
                                                        <div className="row">
                                                            <div className="col-sm-3">
                                                                <h6 className="mb-0">Phone</h6>
                                                            </div>
                                                            <div className="col-sm-9 text-secondary">
                                                                <ul className="style-type-none">
                                                                    {user.phoneNumberList?.map((phone, index) => (
                                                                        <li key={index}>{phone}</li>
                                                                    ))}
                                                                </ul>
                                                            </div>
                                                        </div>
                                                <hr></hr>
                                                            <div className="row">
                                                                <div className="col-sm-3">
                                                                    <h6 className="mb-0">Skills</h6>
                                                                </div>
                                                                <div className="col-sm-9 text-secondary">
                                                                    <ul>
                                                                        {cv?.skills?.map(skill => (
                                                                            <li key={skill}>{skill}</li>
                                                                        ))}
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                            <div className="row">
                                                                <div className="col-sm-12">
                                                                    <button className="btn btn-info" onClick={handleOpenPersonalModal}>Edit</button>
                                                                    <PersonalModal show={personalModal} onClose={handleClosePersonalModal} user={user} cv={cv}/>
                                                                </div>
                                                            </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                            <div className="card experienceCard" id="academicBackground">
                                <div className="card-body align-items-center justify-content-between">
                                    <h6 className="mb-3 mt-3">Academic Background</h6>
                                    <ul className="text-secondary">
                                        {cv?.academicBackground?.map((academicBackground, index) => (
                                            <li key={index} style={{ marginBottom: '10px' }}>
                                                {formatDate(academicBackground.startDate)} - {formatDate(academicBackground.endDate)} <br />
                                                <strong>{academicBackground.institution}</strong>, {academicBackground.specialization}
                                            </li>
                                        ))}
                                    </ul>
                                </div>
                                <div className="card-footer">
                                    <button className="btn btn-info" onClick={handleOpenAcademicBackgroundModal}>Edit</button>
                                    <AcademicBackgroundModal show={academicBackgroundModal} onClose={handleCloseAcademicBackgroundModal} cv={cv}/>
                                </div>
                            </div>

                            <div className="card experienceCard" id="workExperience">
                                <div className="card-body align-items-center justify-content-between">
                                    <h6 className="mb-3 mt-3">Work Experience</h6>
                                    <ul className="text-secondary">
                                        {cv?.workExperience?.map((workExperience, index) => (
                                            <li key={index} style={{ marginBottom: '10px' }}>
                                                {formatDate(workExperience.startDate)} - {formatDate(workExperience.endDate)} <br />
                                                <strong>{workExperience.company}</strong>, {workExperience.position}
                                            </li>
                                        ))}
                                    </ul>
                                </div>
                                <div className="card-footer">
                                    <button className="btn btn-info" onClick={handleOpenWorkExperienceModal}>Edit</button>
                                    <WorkExperienceModal show={workExperienceModal} onClose={handleCloseWorkExperienceModal} cv={cv}/>
                                </div>
                            </div>

                            <div className="card" id = "projects">
                                <div className="card-body">
                                    <h6 className="d-flex align-items-center mb-3">Projects</h6>
                                    <ul className="text-secondary">
                                        {cv?.projects?.map((project, index) => (
                                            <li key={index}>
                                                <strong>{project.title}</strong> <br /> {project.description}
                                            </li>
                                        ))}
                                    </ul>
                                </div>
                                <div className="row">
                                    <div className="col-sm-12 m-3">
                                        <button className="btn btn-info" onClick={handleOpenProjectsModal}>Edit</button>
                                        <ProjectsModal show={projectsModal} onClose={handleCloseProjectsModal} cv={cv}/>
                                    </div>
                                </div>
                            </div>
                    </div>
        </div>
    )
}

export default CandidateProfile;