import React, { useEffect, useState, useRef, useContext } from 'react';
import '../css/myInterviews.css'
import jwtInterceptor from '../../components/shared/JwtInterceptor';
import InterviewRow from './InterviewRow';

const MyInterviewsPage = () => {
    const [interviews, setInterviews] = useState();

    useEffect(() => {
        jwtInterceptor.get(`http://localhost:8081/api/v1/interview/get/all/my`)
            .then(data => {
                setInterviews(data.data);
            })
            .catch(err => {
                console.log(err);
            })
    }, []);

    if(!interviews){
        return "Loading interviews...";
    }
    
    return (
        <div className="row">
            <div className="col-8 offset-2 mt-5">
                <div className="my-interviews">
                    <h1>My planned interviews</h1>
                    <table className="table mt-5 table-striped">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Created on</th>
                                <th scope="col">Starts at</th>
                                <th scope="col">Participants</th>
                                <th scope="col">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {interviews.map((interview, index) => {
                                return <InterviewRow key={index} index={index+1} interview={interview} />
                            })}
                        </tbody>
                    </table>
                    {interviews.length===0 &&
                            <h5 className='mt-5'>No interviews yet!</h5>
                    }
                </div>
            </div>
        </div>
    )
}


export default MyInterviewsPage;