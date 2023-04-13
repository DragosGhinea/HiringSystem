package ro.hiringsystem.service.impl;

import ro.hiringsystem.model.CandidateUser;
import ro.hiringsystem.model.InterviewerUser;
import ro.hiringsystem.model.dto.AnonymousUserDto;
import ro.hiringsystem.model.dto.InterviewerUserDto;
import ro.hiringsystem.model.enums.InterviewerType;
import ro.hiringsystem.service.InterviewerUserService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InterviewerUserServiceImpl implements InterviewerUserService {

    private static Map<UUID, InterviewerUserDto> interviewerUserMap = new HashMap<>();

    @Override
    public InterviewerUserDto getById(UUID id) {
        Optional<InterviewerUserDto> user = interviewerUserMap.values().stream()
                .filter(element -> id.equals(element.getId()))
                .findAny();

        if(user.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        return user.get();
    }

    @Override
    public Map<UUID, InterviewerUserDto> getByLastName(String lastName) {
        return interviewerUserMap.values().stream()
                .filter(element -> lastName.equals(element.getLastName()))
                .collect(Collectors.toMap(InterviewerUserDto::getId, Function.identity()));
    }

    @Override
    public Map<UUID, InterviewerUserDto> getByType(InterviewerType interviewerType) {
        return interviewerUserMap.values().stream()
                .filter(element -> interviewerType.equals(element.getInterviewerType()))
                .collect(Collectors.toMap(InterviewerUserDto::getId, Function.identity()));
    }

    @Override
    public Map<UUID, InterviewerUserDto> getAllFromMap() {
        return interviewerUserMap;
    }

    @Override
    public void addAllFromGivenMap(Map<UUID, InterviewerUserDto> interviewerUserMap) {
        InterviewerUserServiceImpl.interviewerUserMap.putAll(interviewerUserMap);
    }

    @Override
    public void add(InterviewerUserDto interviewerUser) {
        interviewerUserMap.put(interviewerUser.getId(), interviewerUser);
    }

    @Override
    public void removeElementById(UUID id) {
        interviewerUserMap = interviewerUserMap.entrySet().stream()
                .filter(element -> !id.equals(element.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void updateElementById(UUID id, InterviewerUserDto newInterviewerUser) {
            interviewerUserMap.put(newInterviewerUser.getId(), newInterviewerUser);
    }

}
