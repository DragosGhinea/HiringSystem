package ro.hiringsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.hiringsystem.model.Job;
import ro.hiringsystem.model.dto.JobDto;

@Mapper
public interface JobMapper {
    JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);

    JobDto toDto(Job job);

    Job toEntity(JobDto jobDto);
}
