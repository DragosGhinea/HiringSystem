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
import ro.hiringsystem.model.dto.interview.messaging.InterviewMessage;
import ro.hiringsystem.model.dto.interview.video.JoinPayload;
import ro.hiringsystem.model.dto.interview.video.SignalPayload;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class InterviewChatController implements ApplicationListener<SessionDisconnectEvent> {

    private final SimpMessagingTemplate simpleMessagingTemplate;
    private final Map<String, Set<String>> users = new HashMap<>();
    private final Map<String, String> usersToRooms = new HashMap<>();

    private final Map<String, String> sessionsToUsers = new HashMap<>();

    @MessageMapping("/interview/room/message/{roomId}")
    private void receiveMessage(@DestinationVariable UUID roomId, @Payload InterviewMessage message){
        simpleMessagingTemplate.convertAndSend("/interview/room/chat/"+roomId,  message);
    }

    @MessageMapping("/interview/room/video/message/{roomID}/join")
    public void joinRoom(@DestinationVariable String roomID, @Payload JoinPayload payload, SimpMessageHeaderAccessor accessor) {
        sessionsToUsers.put(accessor.getSessionId(), payload.getUserId());
        String userId = payload.getUserId();

        if (users.containsKey(roomID)) {
            Set<String> roomUsers = users.get(roomID);
            //if (roomUsers.size() == 4) {
            //    simpleMessagingTemplate.convertAndSendToUser(userId, "/queue/room/full", "");
            //    return;
            //}
            roomUsers.add(userId);
        } else {
            Set<String> roomUsers = new HashSet<>();
            roomUsers.add(userId);
            users.put(roomID, roomUsers);
        }

        usersToRooms.put(userId, roomID);
        List<String> usersInThisRoom = new ArrayList<>(users.get(roomID));
        usersInThisRoom.remove(userId);

        simpleMessagingTemplate.convertAndSendToUser(userId, "interview/room/users", usersInThisRoom);
    }

    @MessageMapping("/interview/room/video/message/{roomID}/signal")
    public void sendSignal(@DestinationVariable String roomID, SignalPayload payload) {
        String userToSignal = payload.getUserToSignal();

        simpleMessagingTemplate.convertAndSendToUser(userToSignal, "/interview/room/user-joined", payload);
    }

    @MessageMapping("/interview/room/video/message/{roomID}/return-signal")
    public void returnSignal(@DestinationVariable String roomID, SignalPayload payload) {
        simpleMessagingTemplate.convertAndSendToUser(payload.getCallerID(), "/interview/room/receiving-returned-signal", payload);
    }

    @MessageMapping("/interview/room/video/message/{roomID}/update-signal")
    public void videoUpdate(@DestinationVariable String roomID, SignalPayload payload) {
        simpleMessagingTemplate.convertAndSendToUser(payload.getId(), "/interview/room/update-signal", payload);
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

        users.get(roomID).remove(userId);
        sessionsToUsers.remove(event.getSessionId());
    }

}
