package ro.hiringsystem.model.dto;

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
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserDto {

    private UUID id;

    private String firstName;

    private String lastName;

    private List<String> mailList;

    private List<String> phoneNumberList;

    private LocalDate birthDate;

    private transient List<UserType> allRoles;

}
