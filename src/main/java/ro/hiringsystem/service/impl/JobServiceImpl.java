package ro.hiringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.hiringsystem.mapper.JobMapper;
import ro.hiringsystem.model.Job;
import ro.hiringsystem.model.dto.JobDto;
import ro.hiringsystem.repository.JobRepository;
import ro.hiringsystem.service.JobService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public JobDto getById(UUID id) {
        Optional<Job> job = jobRepository.findById(id);

        if(job.isEmpty()) {
            throw new RuntimeException("Job not found!");
        }

        return JobMapper.INSTANCE.toDto(job.get());
    }

    @Override
    public Map<UUID, JobDto> getAllFromMap() {
        return listToMap(jobRepository.findAll().stream()
                .map(JobMapper.INSTANCE::toDto).toList());
    }

    @Override
    public void addAllFromGivenMap(Map<UUID, JobDto> jobMap) {
        jobRepository.saveAll(jobMap.values().stream()
                .map(JobMapper.INSTANCE::toEntity).toList());
    }

    @Override
    public void add(JobDto job) {
        jobRepository.save(JobMapper.INSTANCE.toEntity(job));
    }

    @Override
    public void removeElementById(UUID id) {
        Optional<Job> job = jobRepository.findById(id);

        if(job.isEmpty()) {
            throw new RuntimeException("Job not found!");
        }

        else jobRepository.delete(job.get());
    }

    @Override
    public void updateElementById(JobDto newJobDto) {
        Optional<Job> job = jobRepository.findById(newJobDto.getId());

        if(job.isEmpty()) {
            throw new RuntimeException("Job not found!");
        }

        else jobRepository.save(JobMapper.INSTANCE.toEntity(newJobDto));
    }

    @Override
    public Map<UUID, JobDto> listToMap(List<JobDto> jobDtoList) {
        Map<UUID, JobDto> jobDtoMap = new HashMap<>();

        for (JobDto job : jobDtoList) {
            jobDtoMap.put(job.getId(), job);
        }

        return jobDtoMap;
    }
}
