package ro.hiringsystem.model.auxiliary;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.List;

@Getter
@Setter
@Builder
public class CV {

    private File cvFile;

    private List<AcademicExperience> academicBackground;

    private List<WorkExperience> workExperience;

    private List<String> skills;

    private List<Project> projects;

}
