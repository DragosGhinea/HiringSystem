package ro.hiringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.hiringsystem.mapper.JobApplicationMapper;
import ro.hiringsystem.model.dto.JobApplicationDto;
import ro.hiringsystem.model.entity.JobApplication;
import ro.hiringsystem.repository.JobApplicationRepository;
import ro.hiringsystem.service.JobApplicationService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobApplicationMapper jobApplicationMapper;

    @Override
    public JobApplicationDto getById(UUID id) {
        Optional<JobApplication> jobApplication = jobApplicationRepository.findById(id);

        if(jobApplication.isEmpty()) {
            throw new RuntimeException("Job Application not found!");
        }

        return jobApplicationMapper.toDto(jobApplication.get());
    }

    @Override
    public Map<UUID, JobApplicationDto> getAllFromMap() {
        return listToMap(jobApplicationRepository.findAll().stream()
                .map(jobApplicationMapper::toDto).toList());
    }

    @Override
    public void addAllFromGivenMap(Map<UUID, JobApplicationDto> jobApplicationMap) {
        jobApplicationRepository.saveAll(jobApplicationMap.values().stream()
                .map(jobApplicationMapper::toEntity).toList());
    }

    @Override
    public void add(JobApplicationDto jobApplicationDto) {
        jobApplicationRepository.save(jobApplicationMapper.toEntity(jobApplicationDto));
    }

    @Override
    public void removeElementById(UUID id) {
        Optional<JobApplication> jobApplication = jobApplicationRepository.findById(id);

        if(jobApplication.isEmpty()) {
            throw new RuntimeException("Job Application not found!");
        }

        else jobApplicationRepository.delete(jobApplication.get());
    }

    @Override
    public void saveElement(JobApplicationDto jobApplicationDto) {
        JobApplication jobApplication = jobApplicationMapper.toEntity(jobApplicationDto);
        jobApplicationRepository.save(jobApplicationMapper.toEntity(jobApplicationDto));
    }

    @Override
    public Map<UUID, JobApplicationDto> listToMap(List<JobApplicationDto> jobApplicationDtoList) {
        Map<UUID, JobApplicationDto> jobApplicationDtoMap = new HashMap<>();

        for (JobApplicationDto jobApplicationDto : jobApplicationDtoList) {
            jobApplicationDtoMap.put(jobApplicationDto.getId(), jobApplicationDto);
        }

        return jobApplicationDtoMap;
    }
}
