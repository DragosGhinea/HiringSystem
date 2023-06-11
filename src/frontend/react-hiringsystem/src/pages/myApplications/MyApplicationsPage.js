import React, { useEffect, useState, useRef, useContext } from 'react';
import '../css/myApplications.css'
import jwtInterceptor from '../../components/shared/JwtInterceptor';
import ApplicationRow from './ApplicationRow';

const MyApplicationsPage = () => {
    const [applications, setApplications] = useState();

    useEffect(() => {
        jwtInterceptor.get(`http://localhost:8081/api/v1/application/get/all/my`)
            .then(data => {
                console.log(data.data)
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
                    <h1>My applications</h1>
                    <table className="table mt-5 table-striped">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">For job</th>
                                <th scope="col">Submited on</th>
                                <th scope="col">Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            {applications.map((app, index) => {
                                return <ApplicationRow key={index} index={index+1} application={app} />
                            })}
                        </tbody>
                    </table>
                    {applications.length===0 &&
                            <h5 className='mt-5'>No applications yet!</h5>
                    }
                </div>
            </div>
        </div>
    )
}


export default MyApplicationsPage;