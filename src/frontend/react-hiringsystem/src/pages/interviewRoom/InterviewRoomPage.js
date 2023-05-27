import React, { useEffect, useState, useRef } from 'react';
import { Client } from '@stomp/stompjs';
import { useNavigate, useParams } from 'react-router-dom';
import ChatBox from './chat/ChatBox'
import VideoBox from './video/VideoBox'
import SockJS from 'sockjs-client';
import '../css/interviewRoomPage.css'
import jwtInterceptor from "../../components/shared/JwtInterceptor";

const InterviewRoomPage = () => {
  const [userData, setUserData] = useState(null)
  const [stompClient, setStompClient] = useState(null)
  const [connected, setConnected] = useState(false)
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
    
    return () => {
      console.log("LEFT TRIGGERED");
      disconnect();
    };
  }, [userData]);

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

    setStompClient(client);
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
  };

  if(!userData){
    return "Loading data...";
  }

  if(!connected){
    return "Connecting...";
  }

  return (
        <div className="row">
          <div className="col-10 offset-1 mt-5">
            <VideoBox roomId = {id} userData = {userData} stompClient = {stompClient}/>
            <ChatBox roomId = {id} userData = {userData} stompClient = {stompClient} />
          </div>
        </div>
  );
};

export default InterviewRoomPage;