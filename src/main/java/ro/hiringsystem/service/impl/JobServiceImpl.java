package ro.hiringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ro.hiringsystem.mapper.JobMapper;
import ro.hiringsystem.model.dto.JobDto;
import ro.hiringsystem.model.entity.Job;
import ro.hiringsystem.repository.JobRepository;
import ro.hiringsystem.service.JobService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    /**
     * Retrieves a job by its ID.
     *
     * @param id the ID of the job
     * @return the JobDto object
     * @throws RuntimeException if the job is not found
     */
    @Override
    public JobDto getById(UUID id) {
        Optional<Job> job = jobRepository.findById(id);

        if(job.isEmpty()) {
            throw new RuntimeException("Job not found!");
        }

        return jobMapper.toDto(job.get());
    }

    /**
     * Retrieves all jobs as a map with IDs as keys and JobDto objects as values.
     *
     * @return a map of JobDto objects
     */
    @Override
    public Map<UUID, JobDto> getAllFromMap() {
        return listToMap(jobRepository.findAll().stream()
                .map(jobMapper::toDto).toList());
    }

    /**
     * Adds multiple jobs from the given map to the repository.
     *
     * @param jobMap the map of JobDto objects
     */
    @Override
    public void addAllFromGivenMap(Map<UUID, JobDto> jobMap) {
        jobRepository.saveAll(jobMap.values().stream()
                .map(jobMapper::toEntity).toList());
    }

    /**
     * Adds a job to the repository.
     *
     * @param job the JobDto object to be added
     */
    @Override
    public void add(JobDto job) {
        jobRepository.save(jobMapper.toEntity(job));
    }

    /**
     * Removes a job from the repository by its ID.
     *
     * @param id the ID of the job to be removed
     * @throws RuntimeException if the job is not found
     */
    @Override
    public void removeElementById(UUID id) {
        Optional<Job> job = jobRepository.findById(id);

        if(job.isEmpty()) {
            throw new RuntimeException("Job not found!");
        }

        else jobRepository.delete(job.get());
    }

    /**
     * Saves a job in the repository.
     *
     * @param jobDto the JobDto to be saved
     */
    @Override
    public void saveElement(JobDto jobDto) {
        Job job = jobMapper.toEntity(jobDto);
        jobRepository.save(job);
    }

    /**
     * Converts a list of JobDto objects to a map with IDs as keys and JobDto as values.
     *
     * @param jobDtoList the list of JobDto objects
     * @return a map of JobDto objects
     */
    @Override
    public Map<UUID, JobDto> listToMap(List<JobDto> jobDtoList) {
        Map<UUID, JobDto> jobDtoMap = new HashMap<>();

        for (JobDto job : jobDtoList) {
            jobDtoMap.put(job.getId(), job);
        }

        return jobDtoMap;
    }

    /**
     * Creates or updates a job in the repository.
     *
     * @param jobDto the JobDto to be created or updated
     * @return the created or updated JobDto object, or null if an exception occurs
     */
    @Override
    public JobDto createEdit(JobDto jobDto) {
        try {
            saveElement(jobDto);
            return jobDto;
        } catch (Exception x) {
            x.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves all jobs from the repository.
     *
     * @return a list of all JobDto objects
     */
    @Override
    public List<JobDto> getAll() {
        return jobRepository.findAll().stream()
                .map(jobMapper::toDto).toList();
    }

    /**
     * Retrieves a paginated list of jobs from the repository.
     *
     * @param page the page number
     * @param size the number of jobs per page
     * @return a list of paginated JobDto objects
     */
    @Override
    public List<JobDto> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return jobRepository.findAll(pageRequest).stream()
                .map(jobMapper::toDto).toList();
    }
}
