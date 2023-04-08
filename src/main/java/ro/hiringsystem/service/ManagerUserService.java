package ro.hiringsystem.service;

import org.apache.catalina.User;
import ro.hiringsystem.model.InterviewerUser;
import ro.hiringsystem.model.ManagerUser;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ManagerUserService extends UserService<ManagerUser> {

    Map<UUID, ManagerUser> getByLastName(String lastName);

}
