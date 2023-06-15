package ro.hiringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.hiringsystem.mapper.CandidateUserMapper;
import ro.hiringsystem.mapper.JobApplicationMapper;
import ro.hiringsystem.mapper.JobMapper;
import ro.hiringsystem.model.dto.*;
import ro.hiringsystem.model.entity.JobApplication;
import ro.hiringsystem.model.enums.Status;
import ro.hiringsystem.repository.JobApplicationRepository;
import ro.hiringsystem.service.JobApplicationService;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobApplicationMapper jobApplicationMapper;
    private final CandidateUserMapper candidateUserMapper;
    private final JobMapper jobMapper;

    /**
     * Retrieves all job applications for a specific user along with the corresponding job information.
     *
     * @param userId the ID of the user
     * @return a list of JobApplicationWithJobDto containing job application and job information
     */
    @Override
    public List<JobApplicationWithJobDto> getAllByUserId(UUID userId) {
        List<Object[]> jobApplicationList = jobApplicationRepository.findAllByUserIdWithJob(userId);
        List<JobApplicationWithJobDto> jobApplicationWithJobDtoList = new ArrayList<>();
        for(Object[] pair : jobApplicationList){
            JobApplicationDto jobApplicationDto = jobApplicationMapper.toDto((JobApplication) pair[0]);
            JobDto jobDto = jobMapper.toDto((ro.hiringsystem.model.entity.Job) pair[1]);
            jobApplicationWithJobDtoList.add(new JobApplicationWithJobDto(jobApplicationDto, jobDto));
        }
        return jobApplicationWithJobDtoList;
    }

    /**
     * Retrieves a job application by its ID.
     *
     * @param id the ID of the job application
     * @return the JobApplicationDto if found, or throws a RuntimeException if not found
     */
    @Override
    public JobApplicationDto getById(UUID id) {
        Optional<JobApplication> jobApplication = jobApplicationRepository.findById(id);

        if(jobApplication.isEmpty()) {
            throw new RuntimeException("Job Application not found!");
        }

        return jobApplicationMapper.toDto(jobApplication.get());
    }

    /**
     * Retrieves all job applications as a map, with the IDs as keys and JobApplicationDto as values.
     *
     * @return a map of JobApplicationDto objects
     */
    @Override
    public Map<UUID, JobApplicationDto> getAllFromMap() {
        return listToMap(jobApplicationRepository.findAll().stream()
                .map(jobApplicationMapper::toDto).toList());
    }

    /**
     * Adds all job applications from the given map to the repository.
     *
     * @param jobApplicationMap the map of JobApplicationDto objects
     */
    @Override
    public void addAllFromGivenMap(Map<UUID, JobApplicationDto> jobApplicationMap) {
        jobApplicationRepository.saveAll(jobApplicationMap.values().stream()
                .map(jobApplicationMapper::toEntity).toList());
    }

    /**
     * Adds a job application to the repository.
     *
     * @param jobApplicationDto the JobApplicationDto to be added
     */
    @Override
    public void add(JobApplicationDto jobApplicationDto) {
        jobApplicationRepository.save(jobApplicationMapper.toEntity(jobApplicationDto));
    }

    /**
     * Removes a job application from the repository by its ID.
     *
     * @param id the ID of the job application
     */
    @Override
    public void removeElementById(UUID id) {
        Optional<JobApplication> jobApplication = jobApplicationRepository.findById(id);

        if(jobApplication.isEmpty()) {
            throw new RuntimeException("Job Application not found!");
        }

        else jobApplicationRepository.delete(jobApplication.get());
    }

    /**
     * Saves a job application in the repository.
     *
     * @param jobApplicationDto the JobApplicationDto to be saved
     */
    @Override
    public void saveElement(JobApplicationDto jobApplicationDto) {
        JobApplication jobApplication = jobApplicationMapper.toEntity(jobApplicationDto);
        jobApplicationRepository.save(jobApplication);
    }

    /**
     * Converts a list of JobApplicationDto objects to a map with IDs as keys and JobApplicationDto as values.
     *
     * @param jobApplicationDtoList the list of JobApplicationDto objects
     * @return a map of JobApplicationDto objects
     */
    @Override
    public Map<UUID, JobApplicationDto> listToMap(List<JobApplicationDto> jobApplicationDtoList) {
        Map<UUID, JobApplicationDto> jobApplicationDtoMap = new HashMap<>();

        for (JobApplicationDto jobApplicationDto : jobApplicationDtoList) {
            jobApplicationDtoMap.put(jobApplicationDto.getId(), jobApplicationDto);
        }

        return jobApplicationDtoMap;
    }

    /**
     * Creates a new job application with the given job ID and user ID.
     *
     * @param jobId  the ID of the job
     * @param userId the ID of the user
     * @return the created JobApplicationDto, or null if an exception occurs
     */
    @Override
    public JobApplicationDto create(UUID jobId, UUID userId) {
        JobApplicationDto jobApplicationDto = JobApplicationDto.builder()
                .id(UUID.randomUUID())
                .jobId(jobId)
                .candidateUserId(userId)
                .applicationDate(LocalDate.now())
                .status(Status.SUBMITTED)
                .build();
        try {
            saveElement(jobApplicationDto);
            return jobApplicationDto;
        } catch (Exception x) {
            x.printStackTrace();
            return null;
        }
    }

    /**
     * Checks if a user has already applied for a job.
     *
     * @param jobId  the ID of the job
     * @param userId the ID of the user
     * @return true if the user has already applied, false otherwise
     */
    @Override
    public boolean checkIfAlreadyApplied(UUID jobId, UUID userId) {
        return jobApplicationRepository.hasAlreadyApplied(jobId, userId);
    }

    /**
     * Retrieves all job applications for a specific job along with the corresponding user information.
     *
     * @param jobId the ID of the job
     * @return a list of JobApplicationWithUserDto containing job application and user information
     */
    @Override
    public List<JobApplicationWithUserDto> getAllByJobId(UUID jobId) {
        List<Object[]> jobApplicationList = jobApplicationRepository.findAllByJobIdWithUser(jobId);
        List<JobApplicationWithUserDto> jobApplicationWithUserDtos = new ArrayList<>();
        for(Object[] pair : jobApplicationList){
            JobApplicationDto jobApplicationDto = jobApplicationMapper.toDto((JobApplication) pair[0]);
            CandidateUserDto candidateUserDto = candidateUserMapper.toDto((ro.hiringsystem.model.entity.CandidateUser) pair[1]);
            jobApplicationWithUserDtos.add(new JobApplicationWithUserDto(jobApplicationDto, candidateUserDto));
        }
        return jobApplicationWithUserDtos;
    }

    @Override
    public boolean accept(UUID jobApplicationId) {
        try{
            JobApplication jobApplication = jobApplicationRepository.getReferenceById(jobApplicationId);
            jobApplication.setStatus(Status.ACCEPTED);
            jobApplicationRepository.save(jobApplication);
            return true;
        } catch (Exception x) {
            x.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean reject(UUID jobApplicationId) {
        try{
            JobApplication jobApplication = jobApplicationRepository.getReferenceById(jobApplicationId);
            jobApplication.setStatus(Status.DENIED);
            jobApplicationRepository.save(jobApplication);
            return true;
        } catch (Exception x) {
            x.printStackTrace();
            return false;
        }
    }
}
