package ro.hiringsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.hiringsystem.model.InterviewerUser;
import ro.hiringsystem.model.enums.InterviewerType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InterviewerUserRepository extends JpaRepository<InterviewerUser, UUID>  {

    @Query("SELECT u FROM InterviewerUser u WHERE u.id = :id")
    Optional<InterviewerUser> findById(UUID id);

    @Query("SELECT u FROM InterviewerUser u")
    List<InterviewerUser> findAll();

    @Query("SELECT u FROM InterviewerUser u WHERE u.lastName = :lastName")
    List<InterviewerUser> findByLastName(String lastName);

    @Query("SELECT u FROM InterviewerUser u WHERE u.interviewerType = :interviewerType")
    List<InterviewerUser> findByType(InterviewerType interviewerType);

}
