package ro.hiringsystem.mapper;

import org.mapstruct.Mapper;
import ro.hiringsystem.model.dto.JobDto;
import ro.hiringsystem.model.entity.Job;

@Mapper
public interface JobMapper {
    JobDto toDto(Job job);

    Job toEntity(JobDto jobDto);
}
