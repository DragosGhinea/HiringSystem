import { useNavigate } from "react-router-dom";
import { useState, useRef } from "react";
import jwtInterceptor from "../../components/shared/JwtInterceptor";

const CandidateRow = ({user, index}) => {
    const deleteRef = useRef();
    const navigate = useNavigate();
    const [isDeleted, setDeleted] = useState(false)

    const onDeleteClick = () => {
        if(deleteRef.current.innerText === "Are you sure?"){
            jwtInterceptor.post(`http://localhost:8081/api/v1/candidate/delete/${user.id}`)
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
            <th scope="row" className="align-middle">{index}</th>
            <td className="align-middle">{user.firstName} {user.lastName}</td>
            <td className="align-middle">{user.primaryEmail}</td>
            <td className="align-middle">{user.birthDate}</td>
            <td className="align-middle">{user.githubProfileLink? user.githubProfileLink : "Unspecified"}</td>
            <td className="align-middle">{user.linkedInProfileLink? user.linkedInProfileLink : "Unspecified"}</td>
            <td className="align-middle"><div className="btn btn-primary" onClick={() => navigate(`/profile/${user.id}`)}>Go to profile</div></td>
            <td className="align-middle"><div ref={deleteRef} className="btn btn-danger" onClick={onDeleteClick}>Delete</div></td>
        </tr>
    )
}

export default CandidateRow;