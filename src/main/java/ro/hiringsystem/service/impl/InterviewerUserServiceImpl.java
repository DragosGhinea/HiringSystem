package ro.hiringsystem.service.impl;

import ro.hiringsystem.model.CandidateUser;
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
    public Map<UUID, InterviewerUser> getByLastName(String lastName) {
        return interviewerUserMap.values().stream()
                .filter(element -> lastName.equals(element.getLastName()))
                .collect(Collectors.toMap(InterviewerUser::getId, Function.identity()));
    }

    @Override
    public Map<UUID, InterviewerUser> getByType(InterviewerType interviewerType) {
        return interviewerUserMap.values().stream()
                .filter(element -> interviewerType.equals(element.getInterviewerType()))
                .collect(Collectors.toMap(InterviewerUser::getId, Function.identity()));
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
    public void add(InterviewerUser interviewerUser) {
        interviewerUserMap.put(interviewerUser.getId(), interviewerUser);
    }

    @Override
    public void removeElementById(UUID id) {
        interviewerUserMap = interviewerUserMap.entrySet().stream()
                .filter(element -> !id.equals(element.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Boolean updateElementById(UUID id, InterviewerUser newInterviewerUser) {
        if (getById(id).equals(Optional.empty())) {
            return false;
        }
        interviewerUserMap.put(newInterviewerUser.getId(), newInterviewerUser);
        return true;
    }

}
