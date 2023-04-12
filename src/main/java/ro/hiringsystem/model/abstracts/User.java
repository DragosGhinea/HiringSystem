package ro.hiringsystem.model.abstracts;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import ro.hiringsystem.model.enums.UserType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Entity
@Table(name="\"USER\"")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @ElementCollection
    private List<String> mailList;

    @ElementCollection
    private List<String> phoneNumberList;

    private LocalDate birthDate;

    private transient List<UserType> allRoles;

    public User() {

    }

    public User(String firstName, String lastName, List<String> mailList, List<String> phoneNumberList, LocalDate birthDate, List<UserType> allRoles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mailList = mailList;
        this.phoneNumberList = phoneNumberList;
        this.birthDate = birthDate;
    }

}
