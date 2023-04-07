package ro.hiringsystem.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.hiringsystem.model.abstracts.User;

import java.io.File;

@SuperBuilder
@Getter
@Setter
public class ManagerUser extends User {

    private String personalInformation;

}
