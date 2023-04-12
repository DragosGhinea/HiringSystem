package ro.hiringsystem.service.impl;


import org.springframework.stereotype.Service;
import ro.hiringsystem.model.CandidateUser;
import ro.hiringsystem.model.abstracts.User;
import ro.hiringsystem.model.dto.CandidateUserDto;
import ro.hiringsystem.model.dto.UserDto;
import ro.hiringsystem.service.CandidateUserService;
import ro.hiringsystem.service.InterviewerUserService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CandidateUserServiceImpl implements CandidateUserService {

    @Override
    public Map<UUID, CandidateUserDto> getByLastName(String lastName) {
        return null;
    }

    @Override
    public CandidateUserDto getById(UUID id) {
        return null;
    }

    @Override
    public Map<UUID, CandidateUserDto> getAllFromMap() {
        return null;
    }

    @Override
    public void addAllFromGivenMap(Map<UUID, CandidateUserDto> clientMap) {

    }

    @Override
    public void add(CandidateUserDto user) {

    }

    @Override
    public void add(User user) {

    }

    @Override
    public void removeElementById(UUID id) {

    }

    @Override
    public Boolean updateElementById(UUID id, CandidateUserDto newUser) {
        return null;
    }

    @Override
    public UserDto getUserByFirstName(String firstName) {
        return null;
    }
}
