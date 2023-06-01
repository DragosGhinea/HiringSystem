import TextMessage from "./TextMessage";
import { useRef, useEffect, useState } from 'react'

const ChatBox = ({roomId, stompClient, userData}) => {
    const messagesEnd = useRef();
    const newMessageText = useRef("");
    const [chatMessages, setChatMessages] = useState([]);

    useEffect(() => {
        onConnected();

    }, []);

    useEffect(() => {
        scrollToBottom();
    }, [chatMessages]);

    const renderMessages = () => {
        return (
            <div className="chatbox-messages flex-1 p-4">
                {
                    chatMessages.map((msg, index) => (
                        <TextMessage
                            key = {index}
                            message = {msg}
                            direction={msg.user_id === userData.id ? "outgoing" : "incoming"}
                        />
                    ))
                }
                <div ref={messagesEnd} />

            </div>
        )
    }

    const onConnected = () => {
        stompClient.subscribe(`/interview/room/chat/${roomId}`, onMessageReceived);
        userJoin();
    };

    const userJoin = () => {
        const chatMessage = {
            user_id: userData.id,
            sender_full_name: `${userData.firstName} ${userData.lastName}`,
            sender_email: userData.primaryEmail,
            message: 'Has joined the interview room',
            message_type: "JOIN"
        };
    
    
        stompClient.publish({
          destination: `/api/v1/sockets/interview/room/chat/message/${roomId}`,
          body: JSON.stringify(chatMessage),
          skipContentLengthHeader: true,
        });
      };

      const addNewMessage = (text) => {
    
        const chatMessage = {
            user_id: userData.id,
            sender_full_name: `${userData.firstName} ${userData.lastName}`,
            sender_email: userData.primaryEmail,
            message: text,
            message_type: "MESSAGE"
        };
    
        stompClient.publish({
            destination: `/api/v1/sockets/interview/room/chat/message/${roomId}`,
            body: JSON.stringify(chatMessage),
            skipContentLengthHeader: true
        });
      }

      const onMessageReceived = (message) => {
        const payloadData = JSON.parse(message.body);
        setChatMessages((prevMessages) => [...prevMessages, payloadData]);
      };


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
                <button onClick={() => {
                    addNewMessage(newMessageText.current.value);
                    newMessageText.current.value = "";
                }} className="btn border-left-0"><i className="bi bi-send-fill"></i></button>
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
};

export default ChatBox;