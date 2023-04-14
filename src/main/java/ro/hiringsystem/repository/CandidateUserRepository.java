package ro.hiringsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.hiringsystem.model.CandidateUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CandidateUserRepository extends JpaRepository<CandidateUser, UUID> {

    @Query("SELECT u FROM CandidateUser u WHERE u.id = :id")
    Optional<CandidateUser> findById(UUID id);

    @Query("SELECT u FROM CandidateUser u WHERE :mail MEMBER OF u.mailList")
    Optional<CandidateUser> findByEmail(String mail);

    @Query("SELECT u FROM CandidateUser u WHERE u.lastName = :lastName")
    List<CandidateUser> findByLastName(String lastName);

    @Query("SELECT u FROM CandidateUser u")
    List<CandidateUser> findAll();

}
