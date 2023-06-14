package ro.hiringsystem.service;

import ro.hiringsystem.model.dto.InterviewerUserDto;
import ro.hiringsystem.model.enums.InterviewerType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface InterviewerUserService extends UserService<InterviewerUserDto> {

    Map<UUID, InterviewerUserDto> getByLastName(String lastName);

    Map<UUID, InterviewerUserDto> getByType(InterviewerType interviewerType);

    List<InterviewerUserDto> getAll(int page, int size);
}
