package ro.hiringsystem.model.dto.cv;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CVDto {
    private UUID id;

    private File cvFile;

    private List<AcademicExperienceDto> academicBackground;

    private List<WorkExperienceDto> workExperience;

    private List<String> skills;

    private List<ProjectDto> projects;
}
