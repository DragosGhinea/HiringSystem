package ro.hiringsystem.service.impl;


import ro.hiringsystem.model.CandidateUser;
import ro.hiringsystem.model.InterviewerUser;
import ro.hiringsystem.model.dto.CandidateUserDto;
import ro.hiringsystem.model.dto.InterviewerUserDto;
import ro.hiringsystem.service.CandidateUserService;
import ro.hiringsystem.service.InterviewerUserService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CandidateUserServiceImpl implements CandidateUserService {

    private static Map<UUID, CandidateUserDto> candidateUserMap = new HashMap<>();

    @Override
    public CandidateUserDto getById(UUID id) {
        Optional<CandidateUserDto> user = candidateUserMap.values().stream()
                .filter(element -> id.equals(element.getId()))
                .findAny();

        if(user.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        return user.get();
    }

    @Override
    public Map<UUID, CandidateUserDto> getByLastName(String lastName) {
        return candidateUserMap.values().stream()
                .filter(element -> lastName.equals(element.getLastName()))
                .collect(Collectors.toMap(CandidateUserDto::getId, Function.identity()));
    }

    @Override
    public Map<UUID, CandidateUserDto> getAllFromMap() {
        return candidateUserMap;
    }

    @Override
    public void addAllFromGivenMap(Map<UUID, CandidateUserDto> candidateUserMap) {
        CandidateUserServiceImpl.candidateUserMap.putAll(candidateUserMap);
    }

    @Override
    public void add(CandidateUserDto candidateUser) {
        candidateUserMap.put(candidateUser.getId(), candidateUser);
    }

    @Override
    public void removeElementById(UUID id) {
        candidateUserMap = candidateUserMap.entrySet().stream()
                .filter(element -> !id.equals(element.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void updateElementById(UUID id, CandidateUserDto newCandidateUser) {
            candidateUserMap.put(newCandidateUser.getId(), newCandidateUser);
    }

}
