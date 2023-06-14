package ro.hiringsystem.model.dto.interview;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class InterviewParticipantDto {
    private UUID userId;

    private UUID interviewRoomId;

    private Boolean isRoomModerator;
}
