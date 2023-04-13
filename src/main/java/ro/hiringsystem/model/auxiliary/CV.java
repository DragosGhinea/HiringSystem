package ro.hiringsystem.model.auxiliary;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;

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

}
