package ro.hiringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.hiringsystem.mapper.JobApplicationMapper;
import ro.hiringsystem.mapper.JobMapper;
import ro.hiringsystem.model.Job;
import ro.hiringsystem.model.JobApplication;
import ro.hiringsystem.model.dto.JobApplicationDto;
import ro.hiringsystem.model.dto.JobDto;
import ro.hiringsystem.repository.JobApplicationRepository;
import ro.hiringsystem.repository.JobRepository;
import ro.hiringsystem.service.JobApplicationService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;

    @Override
    public JobApplicationDto getById(UUID id) {
        Optional<JobApplication> jobApplication = jobApplicationRepository.findById(id);

        if(jobApplication.isEmpty()) {
            throw new RuntimeException("Job Application not found!");
        }

        return JobApplicationMapper.INSTANCE.toDto(jobApplication.get());
    }

    @Override
    public Map<UUID, JobApplicationDto> getAllFromMap() {
        return listToMap(jobApplicationRepository.findAll().stream()
                .map(JobApplicationMapper.INSTANCE::toDto).toList());
    }

    @Override
    public void addAllFromGivenMap(Map<UUID, JobApplicationDto> jobApplicationMap) {
        jobApplicationRepository.saveAll(jobApplicationMap.values().stream()
                .map(JobApplicationMapper.INSTANCE::toEntity).toList());
    }

    @Override
    public void add(JobApplicationDto jobApplicationDto) {
        jobApplicationRepository.save(JobApplicationMapper.INSTANCE.toEntity(jobApplicationDto));
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
    public void updateElementById(JobApplicationDto newJobApplicationDto) {
        Optional<JobApplication> jobApplication = jobApplicationRepository.findById(newJobApplicationDto.getId());

        if(jobApplication.isEmpty()) {
            throw new RuntimeException("Job Application not found!");
        }

        else jobApplicationRepository.save(JobApplicationMapper.INSTANCE.toEntity(newJobApplicationDto));
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
