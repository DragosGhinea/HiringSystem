package ro.hiringsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import ro.hiringsystem.model.dto.interview.messaging.InterviewMessage;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class InterviewChatController {

    private final SimpMessagingTemplate simpleMessagingTemplate;

    @MessageMapping("/interview/room/message/{roomId}")
    private void receiveMessage(@DestinationVariable UUID roomId, @Payload InterviewMessage message){
        simpleMessagingTemplate.convertAndSend("/interview/room/chat/"+roomId,  message);
    }
}
