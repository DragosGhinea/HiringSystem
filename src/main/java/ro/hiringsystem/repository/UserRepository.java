package ro.hiringsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.hiringsystem.model.abstracts.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u.id FROM User u WHERE :mail MEMBER OF u.mailList")
    UUID findIdByEmail(String mail);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE :mail MEMBER OF u.mailList")
    boolean isEmailUsed(String mail);

    @Query("SELECT u.class FROM User u WHERE u.id = :id")
    Class<?> findTypeById(UUID id);
}
