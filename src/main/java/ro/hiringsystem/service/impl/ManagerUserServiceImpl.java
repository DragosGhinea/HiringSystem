package ro.hiringsystem.service.impl;

import ro.hiringsystem.model.CandidateUser;
import ro.hiringsystem.model.InterviewerUser;
import ro.hiringsystem.model.ManagerUser;
import ro.hiringsystem.model.dto.InterviewerUserDto;
import ro.hiringsystem.model.dto.ManagerUserDto;
import ro.hiringsystem.service.ManagerUserService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ManagerUserServiceImpl implements ManagerUserService {

    private static Map<UUID, ManagerUserDto> managerUserMap = new HashMap<>();

    @Override
    public ManagerUserDto getById(UUID id) {
        Optional<ManagerUserDto> user = managerUserMap.values().stream()
                .filter(element -> id.equals(element.getId()))
                .findAny();

        if(user.isPresent()) {
            throw new RuntimeException("User not found!");
        }

        return user.get();
    }

    @Override
    public Map<UUID, ManagerUserDto> getByLastName(String lastName) {
        return managerUserMap.values().stream()
                .filter(element -> lastName.equals(element.getLastName()))
                .collect(Collectors.toMap(ManagerUserDto::getId, Function.identity()));
    }

    @Override
    public Map<UUID, ManagerUserDto> getAllFromMap() {
        return managerUserMap;
    }

    @Override
    public void addAllFromGivenMap(Map<UUID, ManagerUserDto> managerUserMap) {
        ManagerUserServiceImpl.managerUserMap.putAll(managerUserMap);
    }

    @Override
    public void add(ManagerUserDto managerUser) {
        managerUserMap.put(managerUser.getId(), managerUser);
    }

    @Override
    public void removeElementById(UUID id) {
        managerUserMap = managerUserMap.entrySet().stream()
                .filter(element -> !id.equals(element.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void updateElementById(UUID id, ManagerUserDto newManagerUser) {
            managerUserMap.put(newManagerUser.getId(), newManagerUser);
    }

}
