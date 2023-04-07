package ro.hiringsystem.service;

import ro.hiringsystem.model.CandidateUser;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface CandidateUserService {

    Optional<CandidateUser> getById(UUID id);

    Optional<CandidateUser> getByLastName(Optional<String> lastName);

    Map<UUID, CandidateUser> getAllFromMap();

    void addAllFromGivenMap(Map<UUID, CandidateUser> candidateUserMap);

    void addOnlyOne(CandidateUser candidateUser);

    void removeElementById(UUID id);

    void updateElementById(UUID id, CandidateUser newCandidateUser);

}
