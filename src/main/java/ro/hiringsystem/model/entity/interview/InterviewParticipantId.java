package ro.hiringsystem.model.entity.interview;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InterviewParticipantId implements Serializable {
    private UUID userId;
    private UUID interviewRoomId;
}
