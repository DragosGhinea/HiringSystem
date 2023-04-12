package ro.hiringsystem.service.impl;

import org.springframework.stereotype.Service;
import ro.hiringsystem.model.AnonymousUser;
import ro.hiringsystem.model.CandidateUser;
import ro.hiringsystem.model.abstracts.User;
import ro.hiringsystem.model.dto.AnonymousUserDto;
import ro.hiringsystem.model.dto.UserDto;
import ro.hiringsystem.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnonymousUserServiceImpl implements UserService<AnonymousUserDto> {


    @Override
    public AnonymousUserDto getById(UUID id) {
        return null;
    }

    @Override
    public Map<UUID, AnonymousUserDto> getAllFromMap() {
        return null;
    }

    @Override
    public void addAllFromGivenMap(Map<UUID, AnonymousUserDto> clientMap) {

    }

    @Override
    public void add(AnonymousUserDto user) {

    }

    @Override
    public void add(User user) {

    }

    @Override
    public void removeElementById(UUID id) {

    }

    @Override
    public Boolean updateElementById(UUID id, AnonymousUserDto newUser) {
        return null;
    }

    @Override
    public UserDto getUserByFirstName(String firstName) {
        return null;
    }
}
