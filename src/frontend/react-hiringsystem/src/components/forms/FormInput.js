//credit: https://www.youtube.com/watch?v=tIdNeoHniEY
import { useState } from "react";
import "./formInput.css";

const FormInput = (props) => {
    const [focused, setFocused] = useState(false);
    const { label, errorMessage, onChange, id, ...inputProps } = props;

    const handleFocus = (e) => {
        setFocused(true);

        if(props.onBlur)
            props.onBlur(e);
    };

    if (props.type === "radio") {
        return (
            <div className="formInput">
                <label>{label}</label>
                {props.options.map((option, index) => (
                    <div key={index} className="radio-container">
                        <input
                            type="radio"
                            id={option.value}
                            name={inputProps.name}
                            value={option.value}
                            checked={inputProps.value === option.value}
                            onChange={onChange}
                            onBlur={handleFocus}
                            onFocus={() =>
                                inputProps.name === "confirmPassword" && setFocused(true)
                            }
                            focused={focused.toString()}
                        />
                        <label htmlFor={option.value} className="radio-label">
                            {option.label}
                        </label>
                    </div>
                ))}
                <span>{errorMessage}</span>
            </div>
        );
    }


    return (
        <div className="formInput">
            <label>{label}</label>
            <input
                {...inputProps}
                onChange={onChange}
                onBlur={handleFocus}
                onFocus={() =>
                    inputProps.name === "confirmPassword" && setFocused(true)
                }
                focused={focused.toString()}
            />
            <span>{errorMessage}</span>
        </div>
    );
};

export default FormInput;