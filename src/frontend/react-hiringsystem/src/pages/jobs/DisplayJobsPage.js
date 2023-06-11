import React, { useEffect, useState } from 'react';
import axios from "axios";
import JobBox from './JobBox';
import '../css/displayJobsPage.css'

const DisplayJobsPage = () => {
    const [jobs, setJobs] = useState();

    useEffect(() => {
        axios.get(`http://localhost:8081/api/v1/job/get/all`)
            .then(data => {
                setJobs(data.data);
            })
            .catch(err => {
                console.log(err);
            })
    }, []);

    if(!jobs){
        return "Loading jobs...";
    }

    return (
        <div className="row">
            <div className="col-8 offset-2 mt-5">
                <div className="all-jobs">
                    <h1>All our available jobs</h1>
                    <div className="job-box-container">
                        {jobs.length > 0 ? (
                            jobs.map((job, index) => (
                                <JobBox key={index} index={index + 1} job={job} />
                            ))
                        ) : (
                            <h5 className="mt-5">No jobs yet!</h5>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default DisplayJobsPage;