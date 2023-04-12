package ro.hiringsystem.model.auxiliary;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
public class CV {
    @Id
    private UUID id;

    private File cvFile;

    @OneToMany
    private List<AcademicExperience> academicBackground;

    @OneToMany
    private List<WorkExperience> workExperience;

    @ElementCollection
    private List<String> skills;

    @OneToMany
    private List<Project> projects;

    public CV() {

    }

    public CV(UUID id, File cvFile, List<AcademicExperience> academicBackground, List<WorkExperience> workExperience, List<String> skills, List<Project> projects) {
        this.id = id;
        this.cvFile = cvFile;
        this.academicBackground = academicBackground;
        this.workExperience = workExperience;
        this.skills = skills;
        this.projects = projects;
    }

}
