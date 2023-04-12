package ro.hiringsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public class InterviewerUser extends User {

    @Enumerated(value = EnumType.STRING)
    private InterviewerType interviewerType;

    private String professionalBackground;

}
