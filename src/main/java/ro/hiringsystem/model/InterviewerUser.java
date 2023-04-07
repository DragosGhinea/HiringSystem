package ro.hiringsystem.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.hiringsystem.model.abstracts.User;
import ro.hiringsystem.model.enums.InterviewerType;

import java.net.URL;
import java.util.Optional;

@SuperBuilder
@Getter
@Setter
public class InterviewerUser extends User {

    private InterviewerType interviewerType;

    private String personalInformation;

}
