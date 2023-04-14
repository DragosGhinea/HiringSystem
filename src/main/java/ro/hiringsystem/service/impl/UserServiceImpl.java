package ro.hiringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.hiringsystem.mapper.CandidateUserMapper;
import ro.hiringsystem.mapper.InterviewerUserMapper;
import ro.hiringsystem.mapper.ManagerUserMapper;
import ro.hiringsystem.mapper.UserMapper;
import ro.hiringsystem.model.CandidateUser;
import ro.hiringsystem.model.abstracts.User;
import ro.hiringsystem.model.dto.CandidateUserDto;
import ro.hiringsystem.model.dto.InterviewerUserDto;
import ro.hiringsystem.model.dto.ManagerUserDto;
import ro.hiringsystem.model.dto.UserDto;
import ro.hiringsystem.repository.CandidateUserRepository;
import ro.hiringsystem.repository.InterviewerUserRepository;
import ro.hiringsystem.repository.ManagerUserRepository;
import ro.hiringsystem.service.CandidateUserService;
import ro.hiringsystem.service.InterviewerUserService;
import ro.hiringsystem.service.ManagerUserService;
import ro.hiringsystem.service.UserService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService<UserDto> {

    private final CandidateUserService candidateUserService;

    private final InterviewerUserService interviewerUserService;

    private final ManagerUserService managerUserService;

    @Override
    public UserDto getById(UUID id) {
        try {
            candidateUserService.getById(id);
        } catch(RuntimeException ec) {
            try {
                interviewerUserService.getById(id);
            } catch(RuntimeException ei){
                 managerUserService.getById(id);
            }
        }

        return null;
    }

    @Override
    public UserDto getByEmail(String email) {
        try {
            candidateUserService.getByEmail(email);
        } catch(RuntimeException ec) {
            try {
                interviewerUserService.getByEmail(email);
            } catch(RuntimeException ei){
                managerUserService.getByEmail(email);
            }
        }

        return null;
    }

    @Override
    public Map<UUID, UserDto> getAll() {
        Map<UUID, UserDto> userDtoMap = new HashMap<>();

        userDtoMap.putAll(candidateUserService.getAll());
        userDtoMap.putAll(interviewerUserService.getAll());
        userDtoMap.putAll(managerUserService.getAll());

        return userDtoMap;
    }

    @Override
    public void addAllFromGivenMap(Map<UUID, UserDto> userDtoMap) {
        for (UserDto user : userDtoMap.values()) {
            add(user);
        }
    }

    @Override
    public void add(UserDto user) {
        if (user instanceof CandidateUserDto candidateUserDto) {
            candidateUserService.add(candidateUserDto);
        }
        else if (user instanceof InterviewerUserDto interviewerUserDto) {
            interviewerUserService.add(interviewerUserDto);
        }
        else {
            managerUserService.add((ManagerUserDto) user);
        }
    }

    @Override
    public void removeElementById(UUID id) {
        try {
            candidateUserService.getById(id);
        } catch(RuntimeException ec) {
            try {
                interviewerUserService.getById(id);
            } catch(RuntimeException ei){
                managerUserService.getById(id);
            }
        }
    }

    @Override
    public void updateElementById(UserDto user) {
        if (user instanceof CandidateUserDto candidateUserDto) {
            candidateUserService.updateElementById(candidateUserDto);
        }
        else if (user instanceof InterviewerUserDto interviewerUserDto) {
            interviewerUserService.updateElementById(interviewerUserDto);
        }
        else {
            managerUserService.updateElementById((ManagerUserDto) user);
        }
    }

    @Override
    public Map<UUID, UserDto> listToMap(List<UserDto> userDtoList) {
        Map<UUID, UserDto> userDtoMap = new HashMap<>();

        for (UserDto user : userDtoList) {
            userDtoMap.put(user.getId(), user);
        }

        return userDtoMap;
    }

}
