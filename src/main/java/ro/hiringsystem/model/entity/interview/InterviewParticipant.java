package ro.hiringsystem.model.entity.interview;

import jakarta.persistence.*;
import lombok.*;
import ro.hiringsystem.model.abstracts.User;

import java.util.UUID;

@Entity
@IdClass(InterviewParticipantId.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterviewParticipant {

    @Id
    private UUID userId;

    @Id
    private UUID interviewRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interviewRoomId", insertable = false, updatable = false)
    private InterviewConferenceRoom interviewRoom;

    private Boolean isRoomModerator;

}
