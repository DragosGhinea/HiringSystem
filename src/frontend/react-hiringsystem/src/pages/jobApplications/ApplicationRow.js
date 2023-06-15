import jwtInterceptor from "../../components/shared/JwtInterceptor";

const ApplicationRow = ({index, application, setCV}) => {

    const viewCv = async () => {
        let cvData = await jwtInterceptor.get(`http://localhost:8081/api/v1/candidate/get/cv/${application.candidate_user.id}`);
        cvData.data.user = application.candidate_user;
        setCV(cvData.data);
    }

    const accept = () => {
        jwtInterceptor.post(`http://localhost:8081/api/v1/application/status/update/ACCEPTED/${application.job_application.id}`)
            .then((data) => {
                window.location.reload();
            });
    }

    const deny = () => {
        jwtInterceptor.post(`http://localhost:8081/api/v1/application/status/update/DENIED/${application.job_application.id}`)
            .then((data) => {
                window.location.reload();
            });
    }

    return (
        <tr>
            <th scope="row">{index}</th>
            <td>
                {application.job_application.status==="SUBMITTED"?
                (
                <div className="input-group">
                    <div className="btn btn-success" onClick={accept}>Accept</div>
                    <div className="btn btn-danger" onClick={deny}>Deny</div>
                </div>
                ):(
                    "-"
                )
                }
            </td>
            <td>{application.candidate_user.firstName + " " + application.candidate_user.lastName}</td>
            <td>{application.candidate_user.primaryEmail}</td>
            <td>{application.job_application.applicationDate}</td>
            <td>{application.job_application.status}</td>
            <td><button onClick={viewCv} className="btn btn-primary">View CV</button></td>
        </tr>
    )
}

export default ApplicationRow; 