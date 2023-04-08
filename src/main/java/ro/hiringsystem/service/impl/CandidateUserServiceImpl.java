package ro.hiringsystem.service.impl;


import ro.hiringsystem.model.CandidateUser;
import ro.hiringsystem.service.CandidateUserService;
import ro.hiringsystem.service.InterviewerUserService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CandidateUserServiceImpl implements CandidateUserService {

    private static Map<UUID, CandidateUser> candidateUserMap = new HashMap<>();

    @Override
    public Optional<CandidateUser> getById(UUID id) {
        return candidateUserMap.values().stream()
                .filter(element -> id.equals(element.getId()))
                .findAny();
    }

    @Override
    public Map<UUID, CandidateUser> getByLastName(String lastName) {
        return candidateUserMap.values().stream()
                .filter(element -> lastName.equals(element.getLastName()))
                .collect(Collectors.toMap(CandidateUser::getId, Function.identity()));
    }

    @Override
    public Map<UUID, CandidateUser> getAllFromMap() {
        return candidateUserMap;
    }

    @Override
    public void addAllFromGivenMap(Map<UUID, CandidateUser> candidateUserMap) {
        CandidateUserServiceImpl.candidateUserMap.putAll(candidateUserMap);
    }

    @Override
    public void add(CandidateUser candidateUser) {
        candidateUserMap.put(candidateUser.getId(), candidateUser);
    }

    @Override
    public void removeElementById(UUID id) {
        candidateUserMap = candidateUserMap.entrySet().stream()
                .filter(element -> !id.equals(element.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Boolean updateElementById(UUID id, CandidateUser newCandidateUser) {
        if (getById(id).equals(Optional.empty())) {
            return false;
        }
        candidateUserMap.put(newCandidateUser.getId(), newCandidateUser);
        return true;
    }

}
