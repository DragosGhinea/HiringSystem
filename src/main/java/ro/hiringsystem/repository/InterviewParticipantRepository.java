package ro.hiringsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.hiringsystem.model.entity.interview.InterviewParticipant;
import ro.hiringsystem.model.entity.interview.InterviewParticipantId;

public interface InterviewParticipantRepository extends JpaRepository<InterviewParticipant, InterviewParticipantId> {
}
