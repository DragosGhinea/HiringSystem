import { useNavigate } from "react-router-dom";
import jwtInterceptor from "../../components/shared/JwtInterceptor";
import { useState, useRef } from "react";

const JobRow = ({job, index}) => {
    const deleteRef = useRef()
    const [isDeleted, setDeleted] = useState();
    const navigate = useNavigate()

    const onDeleteClick = () => {
        if(deleteRef.current.innerText === "Are you sure?"){
            jwtInterceptor.post(`http://localhost:8081/api/v1/job/delete?id=${job.id}`)
                .then(data => {
                    setDeleted(true);
                })
        }
        else{
            deleteRef.current.innerText= "Are you sure?";
            setTimeout(() => {
                if(deleteRef.current)
                    deleteRef.current.innerText = "Delete";
            }, 3000)
        }
    }

    if(isDeleted)
        return null;

    return (
        <tr>
            <th scope="row">{index}</th>
            <td>{job.title}</td>
            <td>{job.jobType}</td>
            <td>{job.position}</td>
            <td>{job.salary}</td>
            <td>{job.hoursPerWeek}</td>
            <td><div className="btn btn-primary" onClick={() => navigate(`/jobs/job/${job.id}`)}>Go to job</div></td>
            <td><div className="btn btn-info" onClick={() => navigate(`/jobs/edit/${job.id}`)}>Edit</div></td>
            <td><div ref={deleteRef} className="btn btn-danger" onClick={onDeleteClick}>Delete</div></td>
        </tr>
    )
}

export default JobRow;