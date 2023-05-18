package ro.hiringsystem.model.entity.interview;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class InterviewConferenceRoom {

    @Id
    private UUID id;

    private LocalDateTime startDate;

    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "interviewRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InterviewParticipant> participants;
}
