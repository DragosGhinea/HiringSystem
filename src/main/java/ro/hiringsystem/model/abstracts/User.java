package ro.hiringsystem.model.abstracts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.hiringsystem.model.enums.UserType;

import java.time.LocalDate;
import java.util.AbstractList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class User {

    private UUID id;

    private String firstName;

    private String lastName;

    private List<String> mailList;

    private List<String> phoneNumberList;

    private LocalDate birthDate;

    private transient List<UserType> allRoles;

}
