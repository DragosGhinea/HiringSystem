package ro.hiringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.hiringsystem.model.dto.CandidateUserDto;
import ro.hiringsystem.model.dto.InterviewerUserDto;
import ro.hiringsystem.model.dto.ManagerUserDto;
import ro.hiringsystem.model.dto.UserDto;
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

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user
     * @return the UserDto object
     * @throws RuntimeException if the user is not found
     */
    @Override
    public UserDto getById(UUID id) {
        try {
            return candidateUserService.getById(id);
        } catch(RuntimeException ec) {
            try {
                return interviewerUserService.getById(id);
            } catch(RuntimeException ei){
                return managerUserService.getById(id);
            }
        }
    }

    /**
     * Retrieves a user by email.
     *
     * @param email the email of the user
     * @return the UserDto object
     * @throws RuntimeException if the user is not found
     */
    @Override
    public UserDto getByEmail(String email) {
        try {
            return candidateUserService.getByEmail(email);
        } catch(RuntimeException ec) {
            try {
                return interviewerUserService.getByEmail(email);
            } catch(RuntimeException ei){
                return managerUserService.getByEmail(email);
            }
        }
    }

    /**
     * Retrieves all users as a map with IDs as keys and UserDto objects as values.
     *
     * @return a map of UserDto objects
     */
    @Override
    public Map<UUID, UserDto> getAll() {
        Map<UUID, UserDto> userDtoMap = new HashMap<>();

        userDtoMap.putAll(candidateUserService.getAll());
        userDtoMap.putAll(interviewerUserService.getAll());
        userDtoMap.putAll(managerUserService.getAll());

        return userDtoMap;
    }

    /**
     * Adds multiple users from the given map to the appropriate service.
     *
     * @param userDtoMap the map of UserDto objects
     */
    @Override
    public void addAllFromGivenMap(Map<UUID, UserDto> userDtoMap) {
        for (UserDto user : userDtoMap.values()) {
            add(user);
        }
    }

    /**
     * Adds a user to the appropriate service based on its type.
     *
     * @param user the UserDto object to be added
     */
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

    /**
     * Removes a user from the appropriate service based on its ID.
     *
     * @param id the ID of the user to be removed
     * @throws RuntimeException if the user is not found
     */
    @Override
    public void removeElementById(UUID id) {
        try {
            candidateUserService.removeElementById(id);
        } catch(RuntimeException ec) {
            try {
                interviewerUserService.removeElementById(id);
            } catch(RuntimeException ei){
                managerUserService.removeElementById(id);
            }
        }
    }

    /**
     * Saves a user to the appropriate service based on its type.
     *
     * @param user the UserDto object to be saved
     * @throws RuntimeException if the user is not found
     */
    @Override
    public void saveElement(UserDto user) {
        if (user instanceof CandidateUserDto candidateUserDto) {
            candidateUserService.saveElement(candidateUserDto);
        }
        else if (user instanceof InterviewerUserDto interviewerUserDto) {
            interviewerUserService.saveElement(interviewerUserDto);
        }
        else {
            managerUserService.saveElement((ManagerUserDto) user);
        }
    }

    /**
     * Converts a list of UserDto objects to a map with IDs as keys and UserDto objects as values.
     *
     * @param userDtoList the list of UserDto objects
     * @return a map of UserDto objects
     */
    @Override
    public Map<UUID, UserDto> listToMap(List<UserDto> userDtoList) {
        Map<UUID, UserDto> userDtoMap = new HashMap<>();

        for (UserDto user : userDtoList) {
            userDtoMap.put(user.getId(), user);
        }

        return userDtoMap;
    }

    /**
     * Creates a new user based on its type.
     *
     * @param newUser the UserDto object representing the new user
     * @return the created UserDto object
     */
    @Override
    public UserDto create(UserDto newUser) {
        if (newUser instanceof CandidateUserDto candidateUserDto) {
            return candidateUserService.create(candidateUserDto);
        }
        else if (newUser instanceof InterviewerUserDto interviewerUserDto) {
            return interviewerUserService.create(interviewerUserDto);
        }
        else {
            return managerUserService.create((ManagerUserDto) newUser);
        }
    }
}
