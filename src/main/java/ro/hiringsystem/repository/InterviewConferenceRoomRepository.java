package ro.hiringsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.hiringsystem.model.entity.interview.InterviewConferenceRoom;

import java.util.UUID;

public interface InterviewConferenceRoomRepository extends JpaRepository<InterviewConferenceRoom, UUID> {
}
