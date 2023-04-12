package ro.hiringsystem.service.impl;

import org.springframework.stereotype.Service;
import ro.hiringsystem.model.CandidateUser;
import ro.hiringsystem.model.InterviewerUser;
import ro.hiringsystem.model.ManagerUser;
import ro.hiringsystem.model.abstracts.User;
import ro.hiringsystem.model.dto.ManagerUserDto;
import ro.hiringsystem.model.dto.UserDto;
import ro.hiringsystem.service.ManagerUserService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ManagerUserServiceImpl implements ManagerUserService {
    @Override
    public Map<UUID, ManagerUserDto> getByLastName(String lastName) {
        return null;
    }

    @Override
    public ManagerUserDto getById(UUID id) {
        return null;
    }

    @Override
    public Map<UUID, ManagerUserDto> getAllFromMap() {
        return null;
    }

    @Override
    public void addAllFromGivenMap(Map<UUID, ManagerUserDto> clientMap) {

    }

    @Override
    public void add(ManagerUserDto user) {

    }

    @Override
    public void add(User user) {

    }

    @Override
    public void removeElementById(UUID id) {

    }

    @Override
    public Boolean updateElementById(UUID id, ManagerUserDto newUser) {
        return null;
    }

    @Override
    public UserDto getUserByFirstName(String firstName) {
        return null;
    }
}
