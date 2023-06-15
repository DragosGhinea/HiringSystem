import axios from "axios";
import { createContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import jwtInterceptor from "./JwtInterceptor";

const JobApplicationContext = createContext();

export const JobApplicationContextProvider = ({ children }) => {

    const navigate = useNavigate();

    const createApplication = async (jobId) => {
        await jwtInterceptor.post("http://localhost:8081/api/v1/application/create?jobId=" + jobId);
        navigate("/");
    }

    const deleteApplication = async (jobApplicationId) => {
        await jwtInterceptor.post("http://localhost:8081/api/v1/application/delete?jobApplicationId=" + jobApplicationId);
    }

    return (
        <JobApplicationContext.Provider value={{ createApplication, deleteApplication }}>
            {children}
        </JobApplicationContext.Provider>
    );

};

export default JobApplicationContext;
