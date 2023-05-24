import React, { useEffect, useState, useContext } from 'react';
import { Client } from '@stomp/stompjs';
import { useNavigate, useParams } from 'react-router-dom';
import ChatBox from './chat/ChatBox'
import VideoBox from './video/VideoBox'
import SockJS from 'sockjs-client';
import '../css/interviewRoomPage.css'
import jwtInterceptor from "../../components/shared/JwtInterceptor";

let stompClient = null;

const InterviewRoomPage = () => {
  const [userData, setUserData] = useState(null)
  const [connected, setConnected] = useState(false);
  const [chatMessages, setChatMessages] = useState([]);
  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    if(userData == null){
    jwtInterceptor.get("http://localhost:8081/api/v1/user/getLoggedIn")
      .then(data => {
          setUserData(data.data);
      })
      .catch(err => {
        console.log(err);
      });

      return;
    }

    connect();
    console.log("Connecting...");
    window.addEventListener('beforeunload', handleBeforeUnload);
    
    return () => {
      disconnect();
      window.removeEventListener('beforeunload', handleBeforeUnload);
    };
  }, [userData]);

  const handleBeforeUnload = () => {
    if (userData) {
      const chatMessage = {
        user_id: userData.id,
        sender_full_name: `${userData.firstName} ${userData.lastName}`,
        sender_email: userData.primaryEmail,
        message: 'Has left the interview room',
        message_type: 'LEAVE'
      };
  
      stompClient.publish({
        destination: `/api/v1/sockets/interview/room/message/${id}`,
        body: JSON.stringify(chatMessage),
        skipContentLengthHeader: true
      });
    }
  };

  const connect = () => {
    const sockJS = new SockJS('http://localhost:8081/api/v1/socketEndpoint');
    const client = new Client({
      brokerURL: 'http://localhost:8081/api/v1/socketEndpoint',
      webSocketFactory: () => sockJS,
      reconnectDelay: 5000, // Optional: Adjust the reconnect delay as needed
    });

    client.onConnect = onConnected;
    client.onStompError = onError;
    client.activate();

    stompClient = client;
  };

  const disconnect = () => {
    if (stompClient) {
      stompClient.deactivate();
    }
  };

  const onError = (error) => {
    console.error(error);
  };

  const onConnected = () => {
    setConnected(true);
    stompClient.subscribe(`/interview/room/chat/${id}`, onMessageReceived);
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
      destination: `/api/v1/sockets/interview/room/message/${id}`,
      body: JSON.stringify(chatMessage),
      skipContentLengthHeader: true,
    });
  };

  const addNewMessage = (text) => {
    if(userData == null)
      return;

    const chatMessage = {
        user_id: userData.id,
        sender_full_name: `${userData.firstName} ${userData.lastName}`,
        sender_email: userData.primaryEmail,
        message: text,
        message_type: "MESSAGE"
    };

    stompClient.publish({
        destination: `/api/v1/sockets/interview/room/message/${id}`,
        body: JSON.stringify(chatMessage),
        skipContentLengthHeader: true,
    });
  }

  const onMessageReceived = (message) => {
    const payloadData = JSON.parse(message.body);
    setChatMessages((prevMessages) => [...prevMessages, payloadData]);
  };

  if(!userData){
    return "Loading data..."
  }

  return (
        <div className="row">
          <div className="col-10 offset-1 mt-5">
            <VideoBox />
            <ChatBox messages={chatMessages} addNewMessage={addNewMessage} userId={userData.id}/>
          </div>
        </div>
  );
};

export default InterviewRoomPage;