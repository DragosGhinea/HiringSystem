package ro.hiringsystem.model.dto.interview.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewMessage {
    private String message;

    @JsonProperty("message_type")
    private InterviewMessageType type;

    @JsonProperty("user_id")
    private UUID userId;

    @JsonProperty("sender_full_name")
    private String senderFullName;

    @JsonProperty("sender_email")
    private String senderEmail;
}
