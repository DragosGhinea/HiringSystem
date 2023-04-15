package ro.hiringsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ro.hiringsystem.model.JobApplication;
import ro.hiringsystem.model.dto.JobApplicationDto;

@Mapper
public interface JobApplicationMapper {

    JobApplicationMapper INSTANCE = Mappers.getMapper(JobApplicationMapper.class);

    @Mapping(source = "candidateUser", target = "candidateUser")
    @Mapping(source = "job", target = "job")
    JobApplicationDto toDto(JobApplication jobApplication);

    @Mapping(source = "candidateUser", target = "candidateUser")
    @Mapping(source = "job", target = "job")
    JobApplication toEntity(JobApplicationDto jobApplicationDto);
}
