package ro.hiringsystem.service;

import ro.hiringsystem.model.dto.UserDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserService <T extends UserDto> {

    T getById(UUID id);

    T getByEmail(String email);

    Map<UUID, T> getAll();

    void addAllFromGivenMap(Map<UUID, T> userMap);

    void add(T user);

    void removeElementById(UUID id);

    void saveElement(T newUser);

    Map<UUID, T> listToMap(List<T> userList);

    T create(T newUserDto);

}
