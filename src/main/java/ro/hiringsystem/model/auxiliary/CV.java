package ro.hiringsystem.model.auxiliary;

import jakarta.persistence.*;
import lombok.*;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CV {
    @Id
    @GeneratedValue
    private UUID id;

    private File cvFile;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AcademicExperience> academicBackground;

    @OneToMany(cascade = CascadeType.ALL)
    private List<WorkExperience> workExperience;

    @ElementCollection
    private List<String> skills;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Project> projects;

}
