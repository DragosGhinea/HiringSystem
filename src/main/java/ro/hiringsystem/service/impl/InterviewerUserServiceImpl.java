package ro.hiringsystem.service.impl;

import ro.hiringsystem.model.InterviewerUser;
import ro.hiringsystem.model.enums.InterviewerType;
import ro.hiringsystem.service.InterviewerUserService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InterviewerUserServiceImpl implements InterviewerUserService {

    private static Map<UUID, InterviewerUser> interviewerUserMap = new HashMap<>();

    @Override
    public Optional<InterviewerUser> getById(UUID id) {
        return interviewerUserMap.values().stream()
                .filter(element -> id.equals(element.getId()))
                .findAny();
    }

    @Override
    public Optional<InterviewerUser> getByLastName(Optional<String> lastName) {
        return interviewerUserMap.values().stream()
                .filter(element -> lastName.equals(element.getLastName()))
                .findAny();
    }

    @Override
    public Optional<InterviewerUser> getByType(InterviewerType interviewerType) {
        return interviewerUserMap.values().stream()
                .filter(element -> interviewerType.equals(element.getInterviewerType()))
                .findAny();
    }

    @Override
    public Map<UUID, InterviewerUser> getAllFromMap() {
        return interviewerUserMap;
    }

    @Override
    public void addAllFromGivenMap(Map<UUID, InterviewerUser> interviewerUserMap) {
        InterviewerUserServiceImpl.interviewerUserMap.putAll(interviewerUserMap);
    }

    @Override
    public void addOnlyOne(InterviewerUser interviewerUser) {
        interviewerUserMap.put(interviewerUser.getId(), interviewerUser);
    }

    @Override
    public void removeElementById(UUID id) {
        interviewerUserMap = interviewerUserMap.entrySet().stream()
                .filter(element -> !id.equals(element.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void updateElementById(UUID id, InterviewerUser newCandidateUser) {
        removeElementById(id);
        addOnlyOne(newCandidateUser);
    }

}
