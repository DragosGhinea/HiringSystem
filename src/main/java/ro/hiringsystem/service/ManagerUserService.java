package ro.hiringsystem.service;

import ro.hiringsystem.model.dto.ManagerUserDto;

import java.util.Map;
import java.util.UUID;

public interface ManagerUserService extends UserService<ManagerUserDto> {

    Map<UUID, ManagerUserDto> getByLastName(String lastName);

}
