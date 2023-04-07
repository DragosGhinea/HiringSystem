package ro.hiringsystem.service;

import ro.hiringsystem.model.InterviewerUser;
import ro.hiringsystem.model.enums.InterviewerType;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface InterviewerUserService {

    Optional<InterviewerUser> getById(UUID id);

    Optional<InterviewerUser> getByLastName(Optional<String> lastName);

    Optional<InterviewerUser> getByType(InterviewerType interviewerType);

    Map<UUID, InterviewerUser> getAllFromMap();

    void addAllFromGivenMap(Map<UUID, InterviewerUser> interviewerUserMap);

    void addOnlyOne(InterviewerUser interviewerUser);

    void removeElementById(UUID id);

    void updateElementById(UUID id, InterviewerUser newInterviewerUser);

}
