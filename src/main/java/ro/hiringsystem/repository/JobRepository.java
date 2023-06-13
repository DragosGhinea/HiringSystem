package ro.hiringsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.hiringsystem.model.entity.Job;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {
    @Query("SELECT j FROM Job j WHERE j.id = :id")
    Optional<Job> findById(UUID id);

    @Query("SELECT j FROM Job j")
    List<Job> findAll();

    Page<Job> findAll(Pageable pageable);

}
