package ro.hiringsystem.service.impl;

import ro.hiringsystem.model.AnonymousUser;
import ro.hiringsystem.service.AnonymousUserService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AnonymousUserServiceImpl implements AnonymousUserService {

    private static Map<UUID, AnonymousUser> anonymousUserMap = new HashMap<>();

    @Override
    public Optional<AnonymousUser> getById(UUID id) {
        return anonymousUserMap.values().stream()
                .filter(element -> id.equals(element.getId()))
                .findAny();
    }

    @Override
    public Map<UUID, AnonymousUser> getAllFromMap() {
        return anonymousUserMap;
    }

    @Override
    public void addAllFromGivenMap(Map<UUID, AnonymousUser> anonymousUserMap) {
        AnonymousUserServiceImpl.anonymousUserMap.putAll(anonymousUserMap);
    }

    @Override
    public void addOnlyOne(AnonymousUser anonymousUser) {
        anonymousUserMap.put(anonymousUser.getId(), anonymousUser);
    }

    @Override
    public void removeElementById(UUID id) {
        anonymousUserMap = anonymousUserMap.entrySet().stream()
                .filter(element -> !id.equals(element.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void updateElementById(UUID id, AnonymousUser newAnonymousUser) {
        removeElementById(id);
        addOnlyOne(newAnonymousUser);
    }

}
