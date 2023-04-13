package ro.hiringsystem.service.impl;

import ro.hiringsystem.model.AnonymousUser;
import ro.hiringsystem.model.CandidateUser;
import ro.hiringsystem.model.dto.AnonymousUserDto;
import ro.hiringsystem.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

public class AnonymousUserServiceImpl implements UserService<AnonymousUserDto> {

    private static Map<UUID, AnonymousUserDto> anonymousUserMap = new HashMap<>();

    @Override
    public AnonymousUserDto getById(UUID id) {
        Optional<AnonymousUserDto> user = anonymousUserMap.values().stream()
                .filter(element -> id.equals(element.getId()))
                .findAny();

        if(user.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        return user.get();
    }

    @Override
    public Map<UUID, AnonymousUserDto> getAllFromMap() {
        return anonymousUserMap;
    }

    @Override
    public void addAllFromGivenMap(Map<UUID, AnonymousUserDto> anonymousUserMap) {
        AnonymousUserServiceImpl.anonymousUserMap.putAll(anonymousUserMap);
    }

    @Override
    public void add(AnonymousUserDto anonymousUser) {
        anonymousUserMap.put(anonymousUser.getId(), anonymousUser);
    }

    @Override
    public void removeElementById(UUID id) {
        anonymousUserMap = anonymousUserMap.entrySet().stream()
                .filter(element -> !id.equals(element.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void updateElementById(UUID id, AnonymousUserDto newAnonymousUser) {
        anonymousUserMap.put(newAnonymousUser.getId(), newAnonymousUser);
    }

}
