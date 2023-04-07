package ro.hiringsystem.service;

import ro.hiringsystem.model.ManagerUser;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ManagerUserService {

    Optional<ManagerUser> getById(UUID id);

    Optional<ManagerUser> getByLastName(Optional<String> lastName);

    Map<UUID, ManagerUser> getAllFromMap();

    void addAllFromGivenMap(Map<UUID, ManagerUser> managerUserMap);

    void addOnlyOne(ManagerUser managerUser);

    void removeElementById(UUID id);

    void updateElementById(UUID id, ManagerUser newManagerUser);

}
