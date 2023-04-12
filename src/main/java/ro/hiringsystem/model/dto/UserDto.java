package ro.hiringsystem.model.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class UserDto {

    private UUID id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private List<String> mailList;

    private List<String> phoneNumberList;

    private LocalDate birthDate;

    private List<UserType> allRoles;

}
