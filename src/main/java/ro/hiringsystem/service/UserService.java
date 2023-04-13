package ro.hiringsystem.service;

import ro.hiringsystem.model.abstracts.User;
import ro.hiringsystem.model.dto.UserDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface UserService <T extends UserDto> {

    T getById(UUID id);

    Map<UUID, T> getAllFromMap();

    void addAllFromGivenMap(Map<UUID, T> userMap);

    void add(T user);

    void removeElementById(UUID id);

    void updateElementById(T newUser);

    Map<UUID, T> listToMap(List<T> userList);

}
