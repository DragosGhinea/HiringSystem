package ro.hiringsystem.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.jetbrains.annotations.NotNull;
import ro.hiringsystem.model.abstracts.User;
import ro.hiringsystem.model.auxiliary.CV;
import ro.hiringsystem.model.enums.UserType;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

@SuperBuilder
@Getter
@Setter
@Entity
public class CandidateUser extends User {

    @JdbcTypeCode(SqlTypes.JSON)
    private CV cv;

    private File recommendation;

    private URL githubProfileLink;

    private URL linkedInProfileLink;

    public CandidateUser() {
    }

    public CandidateUser(@NotNull String firstName, @NotNull String lastName, List<String> mailList, List<String> phoneNumberList, LocalDate birthDate, List<UserType> allRoles, CV cv, File recommendation, URL githubProfileLink, URL linkedInProfileLink) {
        super(firstName, lastName, mailList, phoneNumberList, birthDate, allRoles);
        this.cv = cv;
        this.recommendation = recommendation;
        this.githubProfileLink = githubProfileLink;
        this.linkedInProfileLink = linkedInProfileLink;
    }

}
