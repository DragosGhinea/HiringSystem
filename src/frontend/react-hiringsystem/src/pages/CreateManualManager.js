import React, {useState} from "react";
import FormInput from "../components/forms/FormInput";
import {useNavigate} from "react-router-dom";
import axios from "axios";


const CreateManualManager = () => {
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

    const handleSubmit = (e) => {
        e.preventDefault();

        if (isEmailTaken) {
            console.log("Email is already in use.");
            return;
        }

        const managerDto = {
            firstName: values.firstName,
            lastName: values.lastName,
            primaryEmail: values.email,
            mailList: [values.email],
            phoneNumberList: [],
            birthDate: new Date(2000, 1, 1),
            password: values.password,
            professionalBackground: null,
        };

        console.log("Data to send");
        console.log(managerDto);

        axios.post("http://localhost:8081/api/v1/manager/create", managerDto)
            .then(result => {
                navigate(`/manager/profile/${result.data.id}`)

                console.log("Manager created successfully");
                const managerUserDto = result.data;
                console.log(managerUserDto);
            })
            .catch(err => {
                console.log("Error creating manager");
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
        <form onSubmit={handleSubmit}>
            <h1>Create User</h1>
            {inputs.map((input) => (
                <div key={input.id}>
                    <FormInput
                        {...input}
                        value={values[input.name]}
                        onChange={onChange}
                        onBlur={handleBlur}
                    />
                    {input.name === "email" && isEmailTaken && (
                        <span style={{ color: "red", padding: "3px", fontSize: "16px"}}>Email is already in use!</span>
                    )}
                </div>
            ))}
            <br />

            <br />
            <button className="btn btn-success" type="submit">Submit</button>
        </form>
    );
}

export default CreateManualManager;