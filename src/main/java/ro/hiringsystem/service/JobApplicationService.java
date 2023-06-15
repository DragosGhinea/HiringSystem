package ro.hiringsystem.service;

import ro.hiringsystem.model.dto.JobApplicationDto;
import ro.hiringsystem.model.dto.JobApplicationWithJobDto;
import ro.hiringsystem.model.dto.JobApplicationWithUserDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface JobApplicationService {

    JobApplicationDto getById(UUID id);

    Map<UUID, JobApplicationDto> getAllFromMap();

    void addAllFromGivenMap(Map<UUID, JobApplicationDto> jobApplicationMap);

    void add(JobApplicationDto jobApplicationDto);

    void removeElementById(UUID id);

    void saveElement(JobApplicationDto jobApplicationDto);

    Map<UUID, JobApplicationDto> listToMap(List<JobApplicationDto> jobApplicationDtoList);

    JobApplicationDto create(UUID jobId, UUID userId);

    boolean checkIfAlreadyApplied(UUID jobId, UUID userId);

    List<JobApplicationWithUserDto> getAllByJobId(UUID jobId);

    List<JobApplicationWithJobDto> getAllByUserId(UUID userId);

    boolean accept(UUID jobApplicationId);

    boolean reject(UUID jobApplicationId);
}
