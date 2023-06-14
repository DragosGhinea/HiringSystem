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
        jobApplicationRepository.save(jobApplication);
    }

    @Override
    public Map<UUID, JobApplicationDto> listToMap(List<JobApplicationDto> jobApplicationDtoList) {
        Map<UUID, JobApplicationDto> jobApplicationDtoMap = new HashMap<>();

        for (JobApplicationDto jobApplicationDto : jobApplicationDtoList) {
            jobApplicationDtoMap.put(jobApplicationDto.getId(), jobApplicationDto);
        }

        return jobApplicationDtoMap;
    }

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

    @Override
    public boolean checkIfAlreadyApplied(UUID jobId, UUID userId) {
        return jobApplicationRepository.hasAlreadyApplied(jobId, userId);
    }

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
}
