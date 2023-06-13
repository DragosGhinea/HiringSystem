//import Card from "react-bootstrap/Card";
import './css/mainPage.css'
import gearSvg from "./css/images/gear.svg"
import applicationFlow from "./css/images/application_flow.png"
import MainPageParticles from '../components/shared/particles/MainPageParticles';
import { useNavigate } from 'react-router-dom';

const MainPage = () => {
  const navigate = useNavigate();

  const goJobs = () => {
    navigate("/jobs");
  }

  return (
    <>
      <div className="row">
            <div className="col-6 offset-3 mt-5">
                <div className="main-card">
                  <div className="title">
                    <h1>Hiring System Application</h1>
                    <p className="text-muted">You are on the main page of <b>Company Name</b>'s hiring website.</p>
                  </div>
                  <p className="content">
                    You can checkout our available jobs by clicking the button below.
                    If you'd like to apply to any of them you need to have an account with an uploaded CV in it.
                  </p>
                  <img draggable="false" src={applicationFlow} width="80%" style={{borderRadius: '20px'}}/>
                  <div className="go-jobs btn btn-lg" onClick = {goJobs}>Available Jobs</div>
                  <div className="gearGroup1">
                    <img draggable="false" className="gear1" src={gearSvg} alt="gear1" />
                    <img draggable="false" className="gear2" src={gearSvg} alt="gear2" />
                  </div>
                  <div className="gearGroup2">
                    <img draggable="false" className="gear3" src={gearSvg} alt="gear3" />
                    <img draggable="false" className="gear4" src={gearSvg} alt="gear4" />
                  </div>
                </div>
            </div>

            <MainPageParticles />
      </div>
    </>
  );
};
export default MainPage;