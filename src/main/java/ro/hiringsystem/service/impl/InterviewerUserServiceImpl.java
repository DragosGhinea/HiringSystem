package ro.hiringsystem.service.impl;

import org.springframework.stereotype.Service;
import ro.hiringsystem.model.CandidateUser;
import ro.hiringsystem.model.InterviewerUser;
import ro.hiringsystem.model.abstracts.User;
import ro.hiringsystem.model.dto.InterviewerUserDto;
import ro.hiringsystem.model.dto.UserDto;
import ro.hiringsystem.model.enums.InterviewerType;
import ro.hiringsystem.service.InterviewerUserService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class InterviewerUserServiceImpl implements InterviewerUserService {


    @Override
    public Map<UUID, InterviewerUserDto> getByLastName(String lastName) {
        return null;
    }

    @Override
    public Map<UUID, InterviewerUserDto> getByType(InterviewerType interviewerType) {
        return null;
    }

    @Override
    public InterviewerUserDto getById(UUID id) {
        return null;
    }

    @Override
    public Map<UUID, InterviewerUserDto> getAllFromMap() {
        return null;
    }

    @Override
    public void addAllFromGivenMap(Map<UUID, InterviewerUserDto> clientMap) {

    }

    @Override
    public void add(InterviewerUserDto user) {

    }

    @Override
    public void add(User user) {

    }

    @Override
    public void removeElementById(UUID id) {

    }

    @Override
    public Boolean updateElementById(UUID id, InterviewerUserDto newUser) {
        return null;
    }

    @Override
    public UserDto getUserByFirstName(String firstName) {
        return null;
    }
}
