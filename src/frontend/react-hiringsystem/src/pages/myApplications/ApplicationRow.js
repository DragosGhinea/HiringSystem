import { useEffect } from 'react'

const ApplicationRow = ({index, application}) => {

    return (
        <tr>
            <th scope="row">{index}</th>
            <td>{application.job.title}</td>
            <td>{application.job_application.applicationDate}</td>
            <td>{application.job_application.status}</td>
        </tr>
    )
}

export default ApplicationRow;