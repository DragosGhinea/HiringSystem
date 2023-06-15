import React, { useEffect, useState } from 'react';
import '../css/myApplications.css'
import axios from "axios";
import ApplicationRow from './ApplicationRow';
import {useParams} from "react-router-dom";
import CVModal from './CVModal';

const AllJobApplicationsPage = () => {
    const { jobId } = useParams();
    const [applications, setApplications] = useState([]);
    const [cvData, setCvData] = useState()

    useEffect(() => {
        axios.get(`http://localhost:8081/api/v1/application/get/all/${jobId}`)
            .then(data => {
                setApplications(data.data);
            })
            .catch(err => {
                console.log(err);
            })
    }, []);

    if(!applications){
        return "Loading applications...";
    }

    return (
        <div className="row">
            <div className="col-8 offset-2 mt-5">
                <div className="my-applications">
                    <h1>All job applications</h1>
                    <table className="table mt-5 table-striped">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Action</th>
                            <th scope="col">Cancel</th>
                            <th scope="col">Candidate name</th>
                            <th scope="col">Candidate email</th>
                            <th scope="col">Submitted on</th>
                            <th scope="col">Status</th>
                            <th scope="col">CV</th>
                        </tr>
                        </thead>
                        <tbody>
                        {applications.map((app, index) => {
                            return <ApplicationRow key={index} index={index+1} application={app} setCV={setCvData}/>
                        })}
                        </tbody>
                    </table>
                    {applications.length===0 &&
                        <h5 className='mt-5'>No applications yet!</h5>
                    }

                    <CVModal cv = {cvData} setCV = {setCvData} />
                </div>
            </div>
        </div>
    )
}


export default AllJobApplicationsPage;