package ro.hiringsystem.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.hiringsystem.model.abstracts.User;
import ro.hiringsystem.model.auxiliary.CV;

import java.io.File;
import java.net.URL;

@SuperBuilder
@Getter
@Setter
public class CandidateUser extends User {

    private CV cv;

    private File recommendation;

    private URL githubProfileLink;

    private URL linkedInProfileLink;

}
