import TextMessage from "./TextMessage";
import { useRef, useEffect } from 'react'

const ChatBox = ({messages, addNewMessage, userId}) => {
    const messagesEnd = useRef();
    const newMessageText = useRef("");

    useEffect(() => {
        scrollToBottom();
    }, [messages]);

    const renderMessages = () => {
        return (
            <div className="chatbox-messages flex-1 p-4">
                {
                    messages.map((msg, index) => (
                        <TextMessage
                            key = {index}
                            message = {msg}
                            direction={msg.user_id === userId ? "outgoing" : "incoming"}
                        />
                    ))
                }
                <div ref={messagesEnd} />

            </div>
        )
    }

    const renderFooter = () => {
        return (
            <div className="chatbox-input input-group mb-3">
                <textarea
                  type="text"
                  className="rounding-0 border-0"
                  placeholder="Type something..."
                  ref={newMessageText}
                  onKeyDown={e => {
                    if (e.key === "Enter" && !e.shiftKey && newMessageText !== "") {
                      e.preventDefault();
                      addNewMessage(newMessageText.current.value);
                      newMessageText.current.value = "";
                    }
                  }}
                />
                <button className="btn"><i className="bi bi-upload"></i></button>
                <button className="btn border-left-0"><i className="bi bi-send-fill"></i></button>
            </div>
          );
    }

    const scrollToBottom = () => {
        messagesEnd.current.scrollIntoView({ behavior: "smooth" });
    };
    
    
    return (
        <div className="card chatbox">
            <div className="card-body h-full">
                {renderMessages()}
                {renderFooter()}
            </div>
        </div>
    );
}

export default ChatBox;