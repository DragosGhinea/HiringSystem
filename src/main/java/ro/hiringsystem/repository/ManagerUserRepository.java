package ro.hiringsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.hiringsystem.model.entity.ManagerUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ManagerUserRepository extends JpaRepository<ManagerUser, UUID> {

    @Query("SELECT u FROM ManagerUser u WHERE u.id = :id")
    Optional<ManagerUser> findById(UUID id);

    @Query("SELECT u FROM ManagerUser u WHERE :mail MEMBER OF u.mailList")
    Optional<ManagerUser> findByEmail(String mail);

    @Query("SELECT u FROM ManagerUser u WHERE u.lastName = :lastName")
    List<ManagerUser> findByLastName(String lastName);

    @Query("SELECT u FROM ManagerUser u")
    List<ManagerUser> findAll();

}
