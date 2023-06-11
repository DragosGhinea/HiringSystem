package ro.hiringsystem.service;

import ro.hiringsystem.model.dto.JobDto;
import ro.hiringsystem.model.entity.Job;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface JobService{
    JobDto getById(UUID id);

    Map<UUID, JobDto> getAllFromMap();

    void addAllFromGivenMap(Map<UUID, JobDto> jobMap);

    void add(JobDto job);

    void removeElementById(UUID id);

    void saveElement(JobDto jobDto);

    Map<UUID, JobDto> listToMap(List<JobDto> jobDtoList);

    JobDto createEdit(JobDto jobDto);

    List<JobDto> getAll();
}
