import React, { useState, useEffect, useRef } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import InterviewConferenceRoom from './InterviewConferenceRoom';

function DisplayInterviewConferenceRoom() {
  const navigate = useNavigate();

  const { id } = useParams();
  const [interviewConferenceRoom, setInterviewConferenceRoom] = useState(null);

  useEffect(() => {
    const fetchInterviewConferenceRoom = async () => {
      try {
        const response = await axios.get(`http://localhost:8081/api/v1/interview/get/${id}`);
        const interviewData = response.data;
        setInterviewConferenceRoom(interviewData);
      } catch (error) {
        console.error('Error:', error);
      }
    };

    fetchInterviewConferenceRoom();
  }, [id]);

  const deleteErrorMessage = useRef()
  const deleteInterviewConferenceRoom = () => {
    axios.post(`http://localhost:8081/api/v1/interview/delete/${id}`)
      .then(response => {
        console.log('Interview deleted successfully');
        navigate('/');
      })
      .catch(error => {
        console.error('Failed to delete interview:', error);
        deleteErrorMessage.current.textContent = 'Failed to delete interview!';
      });
  };

  return (
    <div>
      {interviewConferenceRoom ? (
        <>
          <InterviewConferenceRoom interviewConferenceRoom={interviewConferenceRoom} />
          <button className="btn btn-info" onClick={() => navigate(`/interviews/edit/${id}`)} style={{marginRight: '20px'}}>Edit</button>
          <button className="btn btn-danger" onClick={deleteInterviewConferenceRoom}>Delete</button>
          <span ref={deleteErrorMessage}></span>
        </>
      ) : (
        <p>Loading interview data...</p>
      )}
    </div>
  );
}

export default DisplayInterviewConferenceRoom;