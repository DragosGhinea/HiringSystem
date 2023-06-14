
const TextMessage = ({direction, message}) => {

    if(message.message_type === "JOIN"){
      return <div className="join">
        {message.sender_full_name} has joined the interview room!
      </div>
    }

    if(message.message_type === "LEAVE"){
      return <div className="leave">
        {message.sender_full_name} has left the interview room!
      </div>
    }
    
    return (
        <div className={direction+" message"}>
          <div className="message-author">
            {message.sender_full_name}
          </div>
          <div className="message-body">
            {message.message}
          </div>
        </div>
      );
}

export default TextMessage;