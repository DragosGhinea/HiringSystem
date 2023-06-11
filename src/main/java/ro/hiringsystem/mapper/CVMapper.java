package ro.hiringsystem.mapper;

import org.mapstruct.Mapper;
import ro.hiringsystem.model.auxiliary.CV;
import ro.hiringsystem.model.dto.cv.CVDto;

@Mapper(uses = {WorkExperienceMapper.class, AcademicExperienceMapper.class, ProjectMapper.class})
public interface CVMapper {

    CVDto toDto(final CV cv);

    CV toEntity(final CVDto cvDto);
}
