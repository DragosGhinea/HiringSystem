import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { format } from 'date-fns';

const InterviewRow = ({interview, index}) => {
    const [creationDate, setCreationDate] = useState();
    const [startDate, setStartDate] = useState();
    const navigate = useNavigate();
    
    const goToInterview = () => {
        navigate(`/interview/room/${interview.id}`)
    }

    useEffect(() => {
        setCreationDate(format(new Date(interview.creationDate), 'MMMM d, yyyy, HH:mm (z)'));

        setStartDate(format(new Date(interview.startDate), 'MMMM d, yyyy, HH:mm (z)'));
    }, [])

    return (
        <tr>
            <th className="align-middle" scope="interview">{index}</th>
            <td className="align-middle">{creationDate}</td>
            <td className="align-middle">{startDate}</td>
            <td className="align-middle">{interview.participants.length}</td>
            <td className="align-middle"><a className="btn btn-primary" onClick={goToInterview}>Go to interview</a></td>
        </tr>
    )
}

export default InterviewRow;