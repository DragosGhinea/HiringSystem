package ro.hiringsystem.mapper;

import ro.hiringsystem.model.auxiliary.Project;
import ro.hiringsystem.model.dto.cv.ProjectDto;

public interface ProjectMapper {

    ProjectDto toDto(Project project);

    Project toEntity(ProjectDto projectDto);
}
