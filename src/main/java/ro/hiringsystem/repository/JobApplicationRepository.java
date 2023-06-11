package ro.hiringsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.hiringsystem.model.entity.JobApplication;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID> {

    @Query("SELECT ja FROM JobApplication ja WHERE ja.id = :id")
    Optional<JobApplication> findById(UUID id);

    @Query("SELECT ja FROM JobApplication ja")
    List<JobApplication> findAll();

    @Query("SELECT ja, j FROM JobApplication ja JOIN Job j ON ja.jobId = j.id WHERE ja.candidateUserId = :userId")
    List<Object[]> findAllByUserIdWithJob(UUID userId);
}
