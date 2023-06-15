import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import FormInput from "../../components/forms/FormInput";
import axios from "axios";

const CreateManualInterviewer = () => {
    const [isEmailTaken, setIsEmailTaken] = useState(false);

    const [interviewerTypes, setInterviewerTypes] = useState([]);
    const [isFormSubmitted, setIsFormSubmitted] = useState(false);

    const navigate = useNavigate();

    const [values, setValues] = useState({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        confirmPassword: "",
        interviewerType: "",
    });

    useEffect(() => {
        fetchInterviewerTypes();
    }, []);

    const fetchInterviewerTypes = async () => {
        try {
            const response = await axios.get("http://localhost:8081/api/v1/interviewer/types");

            const types = response.data;
            setInterviewerTypes(types);
        } catch (error) {
            console.log("Error fetching interviewer types:", error);
        }
    };

    const inputs = [
        {
            id: 1,
            name: "firstName",
            type: "text",
            placeholder: "First Name",
            errorMessage: "This field is required!",
            label: "First Name",
            required: true,
        },
        {
            id: 2,
            name: "lastName",
            type: "text",
            placeholder: "Last Name",
            errorMessage: "This field is required!",
            label: "Last Name",
            required: true,
        },
        {
            id: 3,
            name: "email",
            type: "email",
            placeholder: "Email",
            errorMessage: "It should be a valid email address!",
            label: "Email",
            pattern: "^[\\w\\.-]+@([\\w-]+\\.)+[\\w-]{2,4}$",
            required: true,
        },
        {
            id: 4,
            name: "password",
            type: "password",
            placeholder: "Password",
            errorMessage: "Password should be 8-20 characters and include at least 1 letter, 1 number and 1 special character!",
            label: "Password",
            pattern: `^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$`,
            required: true,
        },
        {
            id: 5,
            name: "confirmPassword",
            type: "password",
            placeholder: "Confirm Password",
            errorMessage: "Passwords don't match!",
            label: "Confirm Password",
            pattern: values.password,
            required: true,
        },
        {
            id: 6,
            name: "interviewerType",
            type: "radio",
            placeholder: "Select an interviewer type",
            errorMessage: "The interviewer type is required!",
            label: "Interviewer Type",
            options: interviewerTypes.map((type) => ({
                label: type,
                value: type,
            })),
            required: true,
        }
    ];

    const handleSubmit = (e) => {
        e.preventDefault();

        setIsFormSubmitted(true);

        if (isEmailTaken) {
            console.log("Email is already in use.");
            return;
        }

        if (!values.interviewerType) {
            console.log("Please select an interviewer type.");
            return;
        }

        const interviewerDto = {
            firstName: values.firstName,
            lastName: values.lastName,
            primaryEmail: values.email,
            mailList: [values.email],
            phoneNumberList: [],
            birthDate: new Date(2000, 1, 1),
            password: values.password,
            interviewerType: values.interviewerType,
            professionalBackground: null,
        };

        console.log("Data to send");
        console.log(interviewerDto);

        axios
            .post("http://localhost:8081/api/v1/interviewer/create", interviewerDto)
            .then(result => {
                navigate(`/interviewer/profile/${result.data.id}`)

                console.log("Interviewer created successfully");
                const managerUserDto = result.data;
                console.log(managerUserDto);
            })
            .catch((err) => {
                console.log("Error creating interviewer");
                console.log(err);
            });
    };

    const onChange = (e) => {
        setValues({ ...values, [e.target.name]: e.target.value });

        if (e.target.name === "email") {
            setIsEmailTaken(false);
        }
    };

    const checkEmailAvailability = async () => {
        try {
            if (values.email !== "") {
                const response = await axios.get(
                    `http://localhost:8081/api/v1/user/${values.email}`
                );

                const isTaken = response.data;
                setIsEmailTaken(isTaken);
                console.log(isTaken);
            }
        } catch (error) {
            console.log("Error checking email availability:", error);
        }
    };

    const handleBlur = async (e) => {
        if (e.target.name === "email" && !isEmailTaken && values.email !== "" && /^[\w-]+@([\w-]+\.)+[\w-]{2,4}$/.test(values.email)) {
            await checkEmailAvailability();
        }
    };

    return (
        <div style={{ display: "flex", justifyContent: "center", alignItems: "center", height: "100vh" }}>
            <div>
                <h1 style={{ marginTop: '20px', marginBottom: '25px' }}>Create Interviewer</h1>
                <form onSubmit={handleSubmit}>
                    <div style={{boxShadow: "rgba(0, 0, 0, 0.19) 0px 10px 20px, rgba(0, 0, 0, 0.23) 0px 6px 6px", padding: "20px",
                            marginBottom: "25px", width: "500px", display: "flex", flexDirection: "column", borderRadius: '20px', alignItems: "center"}}>
                        {inputs.map((input) => (
                            <div key={input.id}>
                                <FormInput
                                    {...input}
                                    value={values[input.name]}
                                    onChange={onChange}
                                    onBlur={handleBlur}
                                />
                                {input.name === "email" && isEmailTaken && (
                                    <span style={{color: "red", padding: "3px", fontSize: "16px",}}>
                                        Email is already in use!
                                    </span>)}

                                {input.name === "interviewerType" &&
                                    !values.interviewerType &&
                                    isFormSubmitted && (
                                        <span style={{color: "red", padding: "3px", fontSize: "16px",}}>
                                            {input.errorMessage}
                                        </span>)}
                            </div>
                        ))}
                        <button className="btn btn-success" type="submit" style={{alignSelf: "flex-end"}}>
                            Submit
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default CreateManualInterviewer;