import React from 'react';

function InterviewConferenceRoom({ interviewConferenceRoom }) {

    const beautifyDateTime = (dateTimeString) => {
        const dateTime = new Date(dateTimeString);
        const date = dateTime.toLocaleDateString();
        const time = dateTime.toLocaleTimeString();
    
        return `${date} ${time}`;
    };

  return (
    <div>
      <h2>Interview Conference Room Details</h2>
      <p>Start Date: {beautifyDateTime(interviewConferenceRoom.startDate)}</p>
      <p>Creation Date: {beautifyDateTime(interviewConferenceRoom.creationDate)}</p>

      <h3>Participants:</h3>
      <ul>
        {interviewConferenceRoom.participants.map(participant => (
          <li key={participant.userId}>
            <p>Full Name: {participant.firstName} {participant.lastName}</p>
            <p>Email: {participant.primaryEmail}</p>
            <p>Is Room Moderator: {participant.isRoomModerator ? 'Yes' : 'No'}</p>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default InterviewConferenceRoom;