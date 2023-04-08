package ro.hiringsystem.service;

import ro.hiringsystem.model.AnonymousUser;
import ro.hiringsystem.model.abstracts.User;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface UserService <T extends User> {

    Optional<T> getById(UUID id);

    Map<UUID, T> getAllFromMap();

    void addAllFromGivenMap(Map<UUID, T> clientMap);

    void add(T user);

    void removeElementById(UUID id);

    Boolean updateElementById(UUID id, T newUser);

}
