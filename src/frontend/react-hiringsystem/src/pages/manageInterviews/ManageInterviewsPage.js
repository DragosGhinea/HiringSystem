import { useEffect, useState } from "react";
import jwtInterceptor from "../../components/shared/JwtInterceptor";
import { Calendar } from "react-big-calendar";
import InterviewsCalendar from "./InterviewsCalendar";

const ManageInterviewsPage = () => {
    const [interviews, setInterviews] = useState()

    useEffect(() => {
        jwtInterceptor.get("http://localhost:8081/api/v1/interview/get/all")
            .then(data => {
                setInterviews(data.data);
            })
    }, [])

    if(!interviews)
        return "Loading interviews..."

    return (
        <>
            <InterviewsCalendar interviews = {interviews} />
        </>
    )
}

export default ManageInterviewsPage;