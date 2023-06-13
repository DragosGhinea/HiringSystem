package ro.hiringsystem.mapper;

import org.mapstruct.Mapper;
import ro.hiringsystem.model.auxiliary.Project;
import ro.hiringsystem.model.dto.cv.ProjectDto;

@Mapper
public interface ProjectMapper {

    ProjectDto toDto(Project project);

    Project toEntity(ProjectDto projectDto);
}
