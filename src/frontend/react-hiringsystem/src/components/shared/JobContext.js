import { createContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import jwtInterceptor from "./JwtInterceptor";
import axios from "axios";

const JobContext = createContext();

export const JobContextProvider = ({ children }) => {

    const navigate = useNavigate();

    const createJob = async (payload) => {
        await jwtInterceptor.post("http://localhost:8081/api/v1/job/create", payload);
        navigate("/jobs/manage");
    };

    const getJob = async (id) => {
        const response = await axios.get("http://localhost:8081/api/v1/job/get?id=" + id);
        return response.data;
    };

    const postJob = async (id, payload) => {
        await jwtInterceptor.post("http://localhost:8081/api/v1/job/edit?id=" + id, payload);
        navigate("/jobs/manage");
    };

    const deleteJob = async (id) => {
        await jwtInterceptor.post("http://localhost:8081/api/v1/job/delete?id=" + id);
        navigate("/jobs/manage");
    };

    return (
        <JobContext.Provider value={{ createJob, getJob, postJob, deleteJob }}>
            {children}
        </JobContext.Provider>
    );

};

export default JobContext;
