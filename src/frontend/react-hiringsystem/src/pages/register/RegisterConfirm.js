import { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import '../css/registerSent.css'
import successImg from '../css/images/emailConfirmed.png'
import errorImg from '../css/images/emailError.svg'

const RegisterConfirm = () => {
    const {id} = useParams();
    const [success, setSuccess] = useState(null);

    useEffect(() => {
        if(success===null){
            axios.post(`http://localhost:8081/api/v1/auth/register/confirm/${id}`)
                .then(answer => {
                    setSuccess(answer.data);
                })
                .catch(err => {
                    console.log(err);
                    setSuccess(false);
                })
        }
    });

    if(success===null){
        return "Loading...";
    }

    if(success===false){
        return (
            <div className="row">
                <div className="col-6 offset-3 mt-5">
                    <div className="register-sent">
                        <h1>Invalid account!</h1>
                        <img src={errorImg} style={{width: "30%", maxHeight: "80%"}}/>
                        <p>Sorry but it seems no account is associated with your link.
                            <br />It might have timed out.</p>
                    </div>
                </div>
            </div>
        );
    }

    return (
        <div className="row">
            <div className="col-6 offset-3 mt-5">
                <div className="register-sent">
                    <img src={successImg} style={{width: "80%", maxHeight: "80%"}}/>
                    <h2>You can login now!</h2>
                </div>
            </div>
        </div>
    );
}

export default RegisterConfirm;