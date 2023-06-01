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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class InterviewChatController implements ApplicationListener<SessionDisconnectEvent> {

    private final SimpMessagingTemplate simpleMessagingTemplate;
    private final Map<String, Map<String, InterviewParticipantExtraUserInfoDto>> users = new HashMap<>();
    private final Map<String, String> usersToRooms = new HashMap<>();

    private final Map<String, String> sessionsToUsers = new HashMap<>();

    @MessageMapping("/interview/room/chat/message/{roomId}")
    private void receiveMessage(@DestinationVariable UUID roomId, @Payload InterviewMessage message){
        simpleMessagingTemplate.convertAndSend("/interview/room/chat/"+roomId,  message);
    }

    @MessageMapping("/interview/room/join/{roomID}")
    private void joinRoom(@DestinationVariable String roomID, @Payload InterviewParticipantExtraUserInfoDto user, SimpMessageHeaderAccessor accessor){
        sessionsToUsers.put(accessor.getSessionId(), user.getUserId().toString());
        String userId = user.getUserId().toString();
        usersToRooms.put(userId, roomID);

        users.compute(roomID, (key, value) -> {
            if(value == null){
                Map<String, InterviewParticipantExtraUserInfoDto> roomUsers = new HashMap<>();
                roomUsers.put(userId, user);
                return roomUsers;
            }
            else{
                value.put(userId, user);
                return value;
            }
        });
    }

    @MessageMapping("/interview/room/video/message/{roomID}/join")
    public void joinRoom(@DestinationVariable String roomID, @Payload JoinPayload payload) {
        String userId = payload.getUserId();

        Map<String, InterviewParticipantExtraUserInfoDto> usersInThisRoom = new HashMap<>(users.get(roomID));
        usersInThisRoom.remove(userId);

        simpleMessagingTemplate.convertAndSendToUser(userId, "interview/room/users", usersInThisRoom.values());
    }

    @MessageMapping("/interview/room/video/message/{roomID}/signal")
    public void sendSignal(@DestinationVariable String roomID, SignalPayload payload) {
        String userToSignal = payload.getUserToSignal();
        payload.setExtraUserInfoDto(users.get(roomID).get(payload.getCallerID()));

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

        String roomID = usersToRooms.get(userId);
        SignalPayload payload = new SignalPayload();
        payload.setId(userId);

        simpleMessagingTemplate.convertAndSend("/interview/room/video/message/"+roomID+"/user-left", payload);

        InterviewParticipantExtraUserInfoDto user = users.get(roomID).get(userId);
        simpleMessagingTemplate.convertAndSend("/interview/room/chat/"+roomID,
                new InterviewMessage(
                        user.getFirstName()+" " + user.getLastName() + " has left the room",
                        InterviewMessageType.LEAVE,
                        user.getUserId(),
                        user.getFirstName() + " " + user.getLastName(),
                        user.getPrimaryEmail()
                )
        );

        users.get(roomID).remove(userId);
        sessionsToUsers.remove(event.getSessionId());
    }

}
