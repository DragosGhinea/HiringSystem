package ro.hiringsystem.model;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.List;

@Getter
@Setter
public class CV {

    private File cvFile;

    private List<String> academicBackground;

    private List<String> workExperience;

    private List<String> skills;

    private List<String> projects;

}
