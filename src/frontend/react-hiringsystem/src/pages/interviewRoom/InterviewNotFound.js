import '../css/interviewNotFound.css'
import { useNavigate } from 'react-router-dom';

const InterviewNotFound = () => {
    const navigate = useNavigate();

    const goMainPage = () => {
        navigate("/");
    }

    return (
        <div className="row">
            <div className="col-8 offset-2 mt-5">
                <div className="interview-not-found">
                    <h2>Interview Room Not Found</h2>
                    <p>We apologize, but the interview you are looking for could not be found.</p>
                    <div>This can be caused by:
                        <ul>
                            <li>The room not existing to begin with</li>
                            <li>The room has closed</li>
                            <li>The room exists but you are not part of it</li>
                        </ul>
                    </div>
                    <div onClick={goMainPage} className="btn btn-lg btn-primary">Main Page <i className="bi bi-house-fill"></i></div>
                </div>
            </div>
        </div>
    )
}

export default InterviewNotFound;