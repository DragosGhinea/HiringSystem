import { useContext } from "react";
import AuthContext from "../../components/shared/AuthContext"
import CandidateProfile from "./CandidateProfile";
import InterviewerProfile from "./InterviewerProfile"
import ManagerProfile from "./ManagerProfile"

const ProfilePage = () => {
    const {user} = useContext(AuthContext);

    if(user.userType==="candidate")
        return <CandidateProfile />
    else if(user.userType==="interviewer")
        return <InterviewerProfile />
    else if(user.usertype==="manager")
        return <ManagerProfile />
}

export default ProfilePage;