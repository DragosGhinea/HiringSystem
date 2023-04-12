package ro.hiringsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import ro.hiringsystem.model.abstracts.User;
import ro.hiringsystem.model.enums.InterviewerType;
import ro.hiringsystem.model.enums.UserType;

import java.time.LocalDate;
import java.util.List;

@SuperBuilder
@Getter
@Setter
@Entity
public class InterviewerUser extends User {

    @Enumerated(value = EnumType.STRING)
    private InterviewerType interviewerType;

    private String professionalBackground;

    public InterviewerUser() {

    }

    public InterviewerUser(@NotNull String firstName, @NotNull String lastName, List<String> mailList, List<String> phoneNumberList, LocalDate birthDate, List<UserType> allRoles, InterviewerType interviewerType) {
        super(firstName, lastName, mailList, phoneNumberList, birthDate, allRoles);
        this.interviewerType = interviewerType;
    }

}
