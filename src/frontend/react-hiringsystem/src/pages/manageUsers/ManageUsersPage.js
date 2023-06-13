import jwtInterceptor from "../../components/shared/JwtInterceptor";
import { useState, useEffect } from "react";
import '../css/managerUsersPage.css'
import { useLocation, useNavigate } from "react-router-dom";
import CandidateUserTables from "./CandidateUsersTable";
import InterviewerUserTables from "./InterviewerUsersTable";

const ManageUsersPage = () => {
    const pageSize = 2;
    const navigate = useNavigate();
    const [users,setUsers] = useState();
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const [userType, setUserType] = useState("candidate");

    const [page, setPage] = useState(parseInt(searchParams.get('page')) || 1);

    useEffect(() => {
        setUsers(null);
        jwtInterceptor.get(`http://localhost:8081/api/v1/${userType}/get/all/paginated?page=${page}&size=${pageSize}`)
            .then(data => {
                setUsers(data.data);
            })
    }, [page, userType]);

    const prevPage = () => {
        setPage(prevPage => {
            if(prevPage === 1)
                return prevPage;
            return prevPage - 1;
        });
    }

    const nextPage = () => {
        setPage(prevPage => {
            if(users.length===0){
                return prevPage;
            }
            return prevPage + 1;
        });
    }

    if(!users)
        return "Loading users..."

    return (
        <div className="row">
            <div className="col-10 offset-1 mt-5">
                <div className="manage-users">
                    <h1>Manage users</h1>
                    <div className="input-group mb-3">
                        <div className="btn btn-primary" onClick={prevPage}>Previous</div>
                        <div className="btn btn-primary" onClick={nextPage}>Next</div>
                        <div className="page">Page {page}</div>
                        <div className="btn btn-primary" onClick={() => {navigate(`/${userType}/create`)}}>Create User</div>

                        <div className="button-space"></div>
                        <div className={"btn "+(userType==="candidate"?"btn-dark":"btn-light")} onClick={() => {setUserType("candidate"); setPage(1);}}>Candidates</div>
                        <div className={"btn "+(userType==="interviewer"?"btn-dark":"btn-light")} onClick={() => {setUserType("interviewer"); setPage(1)}}>Interviewers</div>
                    </div>
                    {userType==="candidate" && <CandidateUserTables users={users} startIndex={(page-1)*pageSize}/>}
                    {userType==="interviewer" && <InterviewerUserTables users={users} startIndex={(page-1)*pageSize}/>}
                    {users.length===0 &&
                            <h5 className='mt-5'>No users on this page!</h5>
                    }
                </div>
            </div>
        </div>
    );
}

export default ManageUsersPage;