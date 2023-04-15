package ro.hiringsystem.model.abstracts;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.hiringsystem.security.token.Token;

import java.time.LocalDate;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private List<Token> tokens;

}
