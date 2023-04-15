package ro.hiringsystem.service;

import ro.hiringsystem.model.dto.JobApplicationDto;
import ro.hiringsystem.model.dto.JobDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface JobApplicationService {

    JobApplicationDto getById(UUID id);

    Map<UUID, JobApplicationDto> getAllFromMap();

    void addAllFromGivenMap(Map<UUID, JobApplicationDto> jobApplicationMap);

    void add(JobApplicationDto jobApplicationDto);

    void removeElementById(UUID id);

    void updateElementById(JobApplicationDto newJobApplicationDto);

    Map<UUID, JobApplicationDto> listToMap(List<JobApplicationDto> jobApplicationDtoList);

}
