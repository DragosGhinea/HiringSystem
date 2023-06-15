import '../css/registerSent.css'
import emailSent from '../css/images/emailSent.gif'

const RegisterSent = () => {

    return (
        <div className="row">
            <div className="col-6 offset-3 mt-5">
                <div className="register-sent">
                    <h1>Register request sent!</h1>
                    <img src={emailSent} style={{width: '40%'}}/>
                    <p>We have sent you a request to confirm your account on email.
                        <br />If you have not received it, please try to register again.</p>
                </div>
            </div>
        </div>
    );
}

export default RegisterSent;