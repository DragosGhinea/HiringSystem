package ro.hiringsystem.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.hiringsystem.model.abstracts.User;

import java.io.File;
import java.net.URL;
import java.util.Optional;

@SuperBuilder
@Getter
@Setter
public class CandidateUser extends User {

    private CV cv;

    private File recommendation;

    private URL githubProfileLink;

    private URL linkedInProfileLink;

}
