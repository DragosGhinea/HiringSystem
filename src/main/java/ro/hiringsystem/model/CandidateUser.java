package ro.hiringsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public class CandidateUser extends User {

    @OneToOne
    @JoinTable(name = "cv_id")
    private CV cv;

    private File recommendation;

    private URL githubProfileLink;

    private URL linkedInProfileLink;

}
