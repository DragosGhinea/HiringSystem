package ro.hiringsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.hiringsystem.model.entity.interview.InterviewConferenceRoom;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface InterviewConferenceRoomRepository extends JpaRepository<InterviewConferenceRoom, UUID> {

    void deleteByStartDateBefore(LocalDateTime dateTime);

    @Query("SELECT ir FROM InterviewConferenceRoom ir JOIN ir.participants p WHERE p.user.id = :userId")
    List<InterviewConferenceRoom> getAllByUserId(UUID userId);

}
