package ro.hiringsystem.service.impl;

import ro.hiringsystem.model.CandidateUser;
import ro.hiringsystem.model.InterviewerUser;
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
    public Map<UUID, ManagerUser> getByLastName(String lastName) {
        return managerUserMap.values().stream()
                .filter(element -> lastName.equals(element.getLastName()))
                .collect(Collectors.toMap(ManagerUser::getId, Function.identity()));
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
    public void add(ManagerUser interviewerUser) {
        managerUserMap.put(interviewerUser.getId(), interviewerUser);
    }

    @Override
    public void removeElementById(UUID id) {
        managerUserMap = managerUserMap.entrySet().stream()
                .filter(element -> !id.equals(element.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Boolean updateElementById(UUID id, ManagerUser newManagerUser) {
        if (getById(id).equals(Optional.empty())) {
            return false;
        }
        managerUserMap.put(newManagerUser.getId(), newManagerUser);
        return true;
    }

}
