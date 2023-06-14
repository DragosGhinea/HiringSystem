import React, { useEffect, useState, useRef } from 'react';
import { Client } from '@stomp/stompjs';
import { useNavigate, useParams } from 'react-router-dom';
import ChatBox from './chat/ChatBox'
import VideoBox from './video/VideoBox'
import SockJS from 'sockjs-client';
import '../css/interviewRoomPage.css'
import jwtInterceptor from "../../components/shared/JwtInterceptor";

const InterviewRoomPage = ({muted, cameraOff, participant}) => {
  const [userData, setUserData] = useState(null);
  const [stompClient, setStompClient] = useState(null);
  const [connected, setConnected] = useState(false);
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

    const clientToCleanup = connect();
    
    return () => {
      if(clientToCleanup){
        clientToCleanup.deactivate();
      }
    };
  }, [userData]);

  const connect = () => {
    const sockJS = new SockJS('http://localhost:8081/api/v1/socketEndpoint');
    const client = new Client({
      brokerURL: 'http://localhost:8081/api/v1/socketEndpoint',
      webSocketFactory: () => sockJS,
      reconnectDelay: 5000, // Optional: Adjust the reconnect delay as needed
    });
    setStompClient(client);
    return client;
  };

  useEffect(() => {
    if(stompClient){
      stompClient.onConnect = onConnected;
      stompClient.onStompError = onError;

      stompClient.activate();
    }
  }, [stompClient])

  const onError = (error) => {
    console.error(error);
  };

  const roomClosed = (data) => {
    navigate("/interview/room/left?closed=true");
  }

  const onConnected = async () => {
    stompClient.subscribe(`/interview/room/close/${id}`, roomClosed)

    await stompClient.publish({
      destination: `/api/v1/sockets/interview/room/join/${id}`,
      body: userData.id,
      skipContentLengthHeader: true,
    });

    setConnected(true);
  };

  if(!userData){
    return "Loading data...";
  }

  if(!connected)
    return "Connecting...";

  return (
        <div className="row">
          <div className="col-10 offset-1 mt-5">
            <VideoBox muted = {muted} cameraOff = {cameraOff} roomId = {id} userData = {userData} stompClient = {stompClient} participantData = {participant}/>
            <ChatBox roomId = {id} userData = {userData} stompClient = {stompClient} participantData = {participant}/>
          </div>
        </div>
  );
};

export default InterviewRoomPage;