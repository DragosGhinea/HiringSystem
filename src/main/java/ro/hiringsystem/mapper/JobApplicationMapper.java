package ro.hiringsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.hiringsystem.model.dto.JobApplicationDto;
import ro.hiringsystem.model.entity.JobApplication;

@Mapper
public interface JobApplicationMapper {

    // @Mapping(target="candidateUserId", source="candidateUserId")
    JobApplicationDto toDto(JobApplication jobApplication);

    JobApplication toEntity(JobApplicationDto jobApplicationDto);
}
