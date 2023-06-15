import { useContext, useEffect, useState } from "react";
import AuthContext from "../../components/shared/AuthContext"
import CandidateProfile from "./CandidateProfile";
import InterviewerProfile from "./InterviewerProfile"
import ManagerProfile from "./ManagerProfile"
import jwtInterceptor from "../../components/shared/JwtInterceptor";
import { useParams } from "react-router-dom";

const ProfilePage = () => {
    const { id } = useParams();
    const {user} = useContext(AuthContext);
    const [userType, setUserType] = useState();

    useEffect(() => {
        if(id==="me")
            return;
        
        jwtInterceptor.get(`http://localhost:8081/api/v1/user/type/${id}`)
            .then(data => {
                setUserType(data.data.type);
            })
    }, []);

    if(id==="me"){
        if(user.userType==="candidate")
            return <CandidateProfile userId={"me"}/>
        else if(user.userType==="interviewer")
            return <InterviewerProfile userId={"me"}/>
        else if(user.userType==="manager")
            return <ManagerProfile userId={"me"}/>
    }
    else{
        if(userType){
            if(userType==="candidate")
                return <CandidateProfile userId={id}/>
            else if(userType==="interviewer")
                return <InterviewerProfile userId={id}/>
            else if(userType==="manager")
                return <ManagerProfile userId={id}/>
        }
        else{
            return "Loading...";
        }
    }
}

export default ProfilePage;