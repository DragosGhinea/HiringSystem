package ro.hiringsystem.service;

import ro.hiringsystem.model.CandidateUser;
import ro.hiringsystem.model.InterviewerUser;
import ro.hiringsystem.model.dto.InterviewerUserDto;
import ro.hiringsystem.model.enums.InterviewerType;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface InterviewerUserService extends UserService<InterviewerUserDto> {

    Map<UUID, InterviewerUserDto> getByLastName(String lastName);

    Map<UUID, InterviewerUserDto> getByType(InterviewerType interviewerType);

}
