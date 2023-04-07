package ro.hiringsystem.service.impl;


import ro.hiringsystem.model.CandidateUser;
import ro.hiringsystem.service.CandidateUserService;

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
    public Optional<CandidateUser> getByLastName(Optional<String> lastName) {
        return candidateUserMap.values().stream()
                .filter(element -> lastName.equals(element.getLastName()))
                .findAny();
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
    public void addOnlyOne(CandidateUser candidateUser) {
        candidateUserMap.put(candidateUser.getId(), candidateUser);
    }

    @Override
    public void removeElementById(UUID id) {
        candidateUserMap = candidateUserMap.entrySet().stream()
                .filter(element -> !id.equals(element.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void updateElementById(UUID id, CandidateUser newCandidateUser) {
        removeElementById(id);
        addOnlyOne(newCandidateUser);
    }

}
