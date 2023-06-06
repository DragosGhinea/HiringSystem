import { useLocation, useNavigate } from 'react-router-dom';
import '../css/interviewLeft.css'

const InterviewLeft = () => {

    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);

    const closed = queryParams.get('closed');
    const kicked = queryParams.get('kicked');
    const navigate = useNavigate();

    const goMainPage = () => {
        navigate("/");
    }

    if(closed === "true"){
        return (
            <div className="row">
                <div className="col-8 offset-2 mt-5">
                    <div className="interview-left">
                        <h2>Interview room closed!</h2>
                        <p>
                            Thank you for your participation! The interview was marked as finished by a room moderator.
                        </p>
                        <p>
                            You should expect to hear back from us within a maximum of 5 working days via email.
                        </p>
                        <div onClick={goMainPage} className="btn btn-lg btn-primary">Main Page <i className="bi bi-house-fill"></i></div>
                    </div>
                </div>
            </div>
        )
    }

    if(kicked === "true"){
        return (
            <div className="row">
                <div className="col-8 offset-2 mt-5">
                    <div className="interview-left">
                        <h2>You have been kicked from the interview room!</h2>
                        <div onClick={goMainPage} className="btn btn-lg btn-primary">Main Page <i className="bi bi-house-fill"></i></div>
                    </div>
                </div>
            </div>
        )
    }

    return (
        <div className="row">
            <div className="col-8 offset-2 mt-5">
                <div className="interview-left">
                    <h2>You have left the interview!</h2>
                    <p>
                        You have exited the interview room. If you left by mistake, you can simply rejoin using the same link. Otherwise, we appreciate your participation!
                    </p>
                    <p>
                        You should expect to hear back from us within a maximum of 5 working days via email.
                    </p>
                    <div onClick={goMainPage} className="btn btn-lg btn-primary">Main Page <i className="bi bi-house-fill"></i></div>
                </div>
            </div>
        </div>
    )
}

export default InterviewLeft;