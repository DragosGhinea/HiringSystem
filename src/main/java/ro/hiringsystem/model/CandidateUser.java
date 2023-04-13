package ro.hiringsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.hiringsystem.model.abstracts.User;
import ro.hiringsystem.model.auxiliary.CV;

import java.io.File;
import java.net.URL;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CandidateUser extends User {

    @OneToOne
    private CV cv;

    private File recommendation;

    private URL githubProfileLink;

    private URL linkedInProfileLink;

}
