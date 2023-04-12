package ro.hiringsystem.service;

import ro.hiringsystem.model.abstracts.User;
import ro.hiringsystem.model.dto.UserDto;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface UserService <T extends UserDto> {

    T getById(UUID id);

    Map<UUID, T> getAllFromMap();

    void addAllFromGivenMap(Map<UUID, T> clientMap);

    void add(T user);

    void add(User user);

    void removeElementById(UUID id);

    Boolean updateElementById(UUID id, T newUser);

    UserDto getUserByFirstName(String firstName);

}
