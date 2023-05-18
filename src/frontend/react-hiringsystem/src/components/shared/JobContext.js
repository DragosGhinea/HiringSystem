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

    return (
        <JobContext.Provider value={{ create }}>
            {children}
        </JobContext.Provider>
    );

};

export default JobContext;
