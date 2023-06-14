package ro.hiringsystem.mapper;

import org.mapstruct.Mapper;
import ro.hiringsystem.model.auxiliary.AcademicExperience;
import ro.hiringsystem.model.dto.cv.AcademicExperienceDto;

@Mapper
public interface AcademicExperienceMapper {

    AcademicExperienceDto toDto(AcademicExperience academicExperience);

    AcademicExperience toEntity(AcademicExperienceDto academicExperienceDto);
}
