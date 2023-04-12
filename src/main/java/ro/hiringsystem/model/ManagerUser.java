package ro.hiringsystem.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import ro.hiringsystem.model.abstracts.User;
import ro.hiringsystem.model.enums.UserType;

import java.time.LocalDate;
import java.util.List;

@SuperBuilder
@Getter
@Setter
@Entity
public class ManagerUser extends User {

    private String professionalBackground;

    public ManagerUser() {
    }

    public ManagerUser(@NotNull String firstName, @NotNull String lastName, List<String> mailList, List<String> phoneNumberList, LocalDate birthDate, List<UserType> allRoles, String professionalBackground) {
        super(firstName, lastName, mailList, phoneNumberList, birthDate, allRoles);
        this.professionalBackground = professionalBackground;
    }

}
