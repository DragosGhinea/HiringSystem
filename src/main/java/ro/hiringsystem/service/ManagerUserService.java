package ro.hiringsystem.service;

import org.apache.catalina.User;
import ro.hiringsystem.model.InterviewerUser;
import ro.hiringsystem.model.ManagerUser;
import ro.hiringsystem.model.dto.ManagerUserDto;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ManagerUserService extends UserService<ManagerUserDto> {

    Map<UUID, ManagerUserDto> getByLastName(String lastName);

}
