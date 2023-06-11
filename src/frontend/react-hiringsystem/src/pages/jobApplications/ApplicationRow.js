const ApplicationRow = ({index, application}) => {

    return (
        <tr>
            <th scope="row">{index}</th>
            <td>{application.candidate_user.firstName + " " + application.candidate_user.lastName}</td>
            <td>{application.candidate_user.primaryEmail}</td>
            <td>{application.job_application.applicationDate}</td>
            <td>{application.job_application.status}</td>
            <td><button className="btn btn-primary">View CV</button></td>
        </tr>
    )
}

export default ApplicationRow; 