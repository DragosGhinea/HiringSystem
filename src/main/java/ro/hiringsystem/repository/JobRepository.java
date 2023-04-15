package ro.hiringsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.hiringsystem.model.CandidateUser;
import ro.hiringsystem.model.Job;
import ro.hiringsystem.model.enums.JobType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {
    @Query("SELECT j FROM Job j WHERE j.id = :id")
    Optional<Job> findById(UUID id);

    @Query("SELECT j FROM Job j")
    List<Job> findAll();

}
