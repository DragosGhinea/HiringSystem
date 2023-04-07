package ro.hiringsystem.service.impl;

import ro.hiringsystem.model.ManagerUser;
import ro.hiringsystem.service.ManagerUserService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ManagerUserServiceImpl implements ManagerUserService {

    private static Map<UUID, ManagerUser> managerUserMap = new HashMap<>();

    @Override
    public Optional<ManagerUser> getById(UUID id) {
        return managerUserMap.values().stream()
                .filter(element -> id.equals(element.getId()))
                .findAny();
    }

    @Override
    public Optional<ManagerUser> getByLastName(Optional<String> lastName) {
        return managerUserMap.values().stream()
                .filter(element -> lastName.equals(element.getLastName()))
                .findAny();
    }

    @Override
    public Map<UUID, ManagerUser> getAllFromMap() {
        return managerUserMap;
    }

    @Override
    public void addAllFromGivenMap(Map<UUID, ManagerUser> managerUserMap) {
        ManagerUserServiceImpl.managerUserMap.putAll(managerUserMap);
    }

    @Override
    public void addOnlyOne(ManagerUser interviewerUser) {
        managerUserMap.put(interviewerUser.getId(), interviewerUser);
    }

    @Override
    public void removeElementById(UUID id) {
        managerUserMap = managerUserMap.entrySet().stream()
                .filter(element -> !id.equals(element.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void updateElementById(UUID id, ManagerUser newManagerUser) {
        removeElementById(id);
        addOnlyOne(newManagerUser);
    }

}
