package ro.hiringsystem.model.abstracts;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.hiringsystem.model.entity.interview.InterviewParticipant;
import ro.hiringsystem.security.token.Token;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name="_USER")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    private UUID id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String primaryEmail;

    @ElementCollection(fetch=FetchType.EAGER)
    private List<String> mailList;

    @ElementCollection(fetch=FetchType.EAGER)
    private List<String> phoneNumberList;

    private LocalDate birthDate;

    @NonNull
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval = true)
    private List<Token> tokens;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY)
    private List<InterviewParticipant> interviewParticipants = new ArrayList<>();

}
