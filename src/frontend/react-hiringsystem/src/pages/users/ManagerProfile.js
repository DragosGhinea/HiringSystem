import React, {useState, useEffect} from 'react';
import axios from 'axios';
import "../css/interviewer-managerProfile.css"
import {useParams} from "react-router-dom";

function ManagerProfile() {

    const { id } = useParams();

    const [user, setUser] = useState(null);

    useEffect(() => {

        document.body.classList.add('containerInterviewerManager');

        axios
            .get(`http://localhost:8081/api/v1/manager/profile/${id}`)
            .then(function (response) {
                console.log(response);
                setUser(response.data);
            })
            .catch(function (error) {
                console.log(error);
            });

    }, [])

    return (user &&
        <div className="containerInterviewerManager mt-5">
            <div className="row d-flex justify-content-center">
                <div className="col-md-7">
                    <div className="card p-3 py-4">

                        <div className="text-center">
                            <img src={require("../css/images/manager.jpg")} alt="Manager" width="300" className="rounded-circle"/>
                        </div>

                        <div className="text-center">
                            <h4>{user.firstName + ' ' + user.lastName}</h4>

                            <span>Manager</span>

                            <div className="row row-divider m-3">
                                <div className="col-md-6">
                                    <div className="row">
                                        <div className="col-sm-3">
                                            <h6 className="mb-0">Email</h6>
                                        </div>
                                        <div className="col-sm-9 text-secondary">
                                            <ul className="style-type-none">
                                                {user.mailList.map(mail => (
                                                    <li key={mail}>{mail}</li>
                                                ))}
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div className="col-md-6">
                                    <div className="row">
                                        <div className="col-sm-3">
                                            <h6 className="mb-0">Phone</h6>
                                        </div>
                                        <div className="col-sm-9 text-secondary">
                                            <ul className="style-type-none">
                                                {user.phoneNumberList.map(phone => (
                                                    <li key={phone}>{phone}</li>
                                                ))}
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div className="px-4 mt-1">
                                <p className="fonts">{user.professionalBackground}</p>
                            </div>

                            <div className="row">
                                <div className="col-sm-12">
                                    <a className="btn btn-info " target="__blank" href=".">Edit</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ManagerProfile;