import React, { useState } from "react";
import axios from "axios";
import FormInput from "../../components/forms/FormInput";
import { useNavigate } from "react-router-dom";

const CreateManualCandidate = () => {
    const [isEmailTaken, setIsEmailTaken] = useState(false);

    const navigate = useNavigate();

    const [values, setValues] = useState({
        firstName : '',
        lastName: '',
        email: '',
        password: '',
        confirmPassword: '',
    });

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
        }
    ];

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (isEmailTaken) {
            console.log("Email is already in use.");
            return;
        }

        const candidateDto = {
            firstName: values.firstName,
            lastName: values.lastName,
            primaryEmail: values.email,
            mailList: [values.email],
            phoneNumberList: [],
            birthDate:  new Date(2000, 1, 1),
            password: values.password,
        };

        console.log("Data to send");
        console.log(candidateDto);

        try {
            axios.post("http://localhost:8081/api/v1/candidate/create", candidateDto)
                .then(result => {
                    navigate(`/candidate/profile/${result.data.id}`)

                    console.log("User registered successfully");
                })
                .catch(err => {
                    console.log("Error registering user");
                    console.log(err);
                });
        } catch (err) {
            console.log("Error registering user with authentication system: ");
            console.log(err);
        }
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
                <h1 style={{ marginTop: '20px', marginBottom: '25px' }}>Create Candidate</h1>
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

export default CreateManualCandidate;