package ro.hiringsystem.mapper;

import org.mapstruct.Mapper;
import ro.hiringsystem.model.dto.JobApplicationDto;
import ro.hiringsystem.model.entity.JobApplication;

@Mapper
public interface JobApplicationMapper {
    JobApplicationDto toDto(JobApplication jobApplication);

    JobApplication toEntity(JobApplicationDto jobApplicationDto);
}
