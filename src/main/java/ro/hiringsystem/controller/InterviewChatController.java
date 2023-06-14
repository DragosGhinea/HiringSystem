package ro.hiringsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import ro.hiringsystem.model.dto.interview.InterviewParticipantExtraUserInfoDto;
import ro.hiringsystem.model.dto.interview.messaging.InterviewMessage;
import ro.hiringsystem.model.dto.interview.messaging.InterviewMessageType;
import ro.hiringsystem.model.dto.interview.video.JoinPayload;
import ro.hiringsystem.model.dto.interview.video.SignalPayload;
import ro.hiringsystem.service.InterviewManagerService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class InterviewChatController implements ApplicationListener<SessionDisconnectEvent> {

    private final SimpMessagingTemplate simpleMessagingTemplate;
    private final InterviewManagerService interviewManagerService;

    private final Map<String, String> sessionsToUsers = new HashMap<>();

    @MessageMapping("/interview/room/chat/message/{roomId}")
    private void receiveMessage(@DestinationVariable UUID roomId, @Payload InterviewMessage message){
        simpleMessagingTemplate.convertAndSend("/interview/room/chat/"+roomId,  message);
    }

    @MessageMapping("/interview/room/join/{roomID}")
    private void joinRoom(@DestinationVariable UUID roomID, @Payload String userId, SimpMessageHeaderAccessor accessor){
        sessionsToUsers.put(accessor.getSessionId(), userId);
        InterviewParticipantExtraUserInfoDto user = interviewManagerService.tryConnectToInterviewRoom(roomID, UUID.fromString(userId));

        simpleMessagingTemplate.convertAndSendToUser(userId, "/interview/room/joined/"+roomID, user);
    }

    @MessageMapping("/interview/room/video/message/{roomID}/join")
    public void joinRoom(@DestinationVariable UUID roomID, @Payload JoinPayload payload) {
        String userId = payload.getUserId();

        Map<UUID, InterviewParticipantExtraUserInfoDto> usersInThisRoom = new HashMap<>(interviewManagerService.getConnectedInterviewParticipants(roomID));
        usersInThisRoom.remove(UUID.fromString(userId));

        simpleMessagingTemplate.convertAndSendToUser(userId, "interview/room/users", usersInThisRoom.values());
    }

    @MessageMapping("/interview/room/video/message/{roomID}/signal")
    public void sendSignal(@DestinationVariable UUID roomID, SignalPayload payload) {
        String userToSignal = payload.getUserToSignal();
        payload.setExtraUserInfoDto(interviewManagerService.getConnectedInterviewParticipants(roomID).get(UUID.fromString(payload.getCallerID())));

        simpleMessagingTemplate.convertAndSendToUser(userToSignal, "/interview/room/user-joined", payload);
    }

    @MessageMapping("/interview/room/video/message/{roomID}/return-signal")
    public void returnSignal(@DestinationVariable String roomID, SignalPayload payload) {
        simpleMessagingTemplate.convertAndSendToUser(payload.getCallerID(), "/interview/room/receiving-returned-signal", payload);
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        String userId = sessionsToUsers.getOrDefault(event.getSessionId(), null);
        if (userId == null) {
            return;
        }

        UUID roomID = interviewManagerService.getConnectedRoomId(UUID.fromString(userId));
        if(roomID == null){
            return;
        }

        SignalPayload payload = new SignalPayload();
        payload.setId(userId);


        simpleMessagingTemplate.convertAndSend("/interview/room/video/message/"+roomID+"/user-left", payload);

        InterviewParticipantExtraUserInfoDto user = interviewManagerService.getConnectedInterviewParticipants(roomID).get(UUID.fromString(userId));
        simpleMessagingTemplate.convertAndSend("/interview/room/chat/"+roomID,
                new InterviewMessage(
                        user.getFirstName()+" " + user.getLastName() + " has left the room",
                        InterviewMessageType.LEAVE,
                        user.getUserId(),
                        user.getFirstName() + " " + user.getLastName(),
                        user.getPrimaryEmail()
                )
        );

        interviewManagerService.leaveInterviewRoom(roomID, UUID.fromString(userId));
        sessionsToUsers.remove(event.getSessionId());
    }

}
