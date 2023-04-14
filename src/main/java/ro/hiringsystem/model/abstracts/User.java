package ro.hiringsystem.model.abstracts;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="\"USER\"")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @ElementCollection
    private List<String> mailList;

    @ElementCollection
    private List<String> phoneNumberList;

    private LocalDate birthDate;

}
