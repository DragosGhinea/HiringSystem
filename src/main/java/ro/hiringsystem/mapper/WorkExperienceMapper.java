package ro.hiringsystem.mapper;

import org.mapstruct.Mapper;
import ro.hiringsystem.model.auxiliary.WorkExperience;
import ro.hiringsystem.model.dto.cv.WorkExperienceDto;

@Mapper
public interface WorkExperienceMapper {

    WorkExperienceDto toDto(WorkExperience workExperience);

    WorkExperience toEntity(WorkExperienceDto workExperienceDto);

}
