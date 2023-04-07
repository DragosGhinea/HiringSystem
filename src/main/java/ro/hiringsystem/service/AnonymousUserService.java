package ro.hiringsystem.service;

import ro.hiringsystem.model.AnonymousUser;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface AnonymousUserService {

    Optional<AnonymousUser> getById(UUID id);

    Map<UUID, AnonymousUser> getAllFromMap();

    void addAllFromGivenMap(Map<UUID, AnonymousUser> clientMap);

    void addOnlyOne(AnonymousUser anonymousUser);

    void removeElementById(UUID id);

    void updateElementById(UUID id, AnonymousUser newAnonymousUser);

}
