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
@NoArgsConstructor
@AllArgsConstructor
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

}
