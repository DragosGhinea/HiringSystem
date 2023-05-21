import axios from "axios";
import { createContext, useState } from "react";
import { useNavigate } from "react-router-dom";

const JobContext = createContext();

export const JobContextProvider = ({ children }) => {

    const navigate = useNavigate();

    const create = async (payload) => {
        console.log("inainte de axios");
        await axios.post("http://localhost:8081/api/v1/job/create", payload);
        navigate("/");
    };

    const getJob = async (id) => {
        const response = await axios.get("http://localhost:8081/api/v1/job/get?id=" + id);
        return response.data;
    };
    const postJob = async (id, payload) => {
        await axios.post("http://localhost:8081/api/v1/job/edit?id=" + id, payload);
        navigate("/");
    };

    return (
        <JobContext.Provider value={{ create, getJob, postJob }}>
            {children}
        </JobContext.Provider>
    );

};

export default JobContext;
