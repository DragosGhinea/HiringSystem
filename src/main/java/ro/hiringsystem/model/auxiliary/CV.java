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
    private UUID id = UUID.randomUUID();

    private File cvFile;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AcademicExperience> academicBackground;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkExperience> workExperience;

    @ElementCollection
    private List<String> skills;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;

    public CV(UUID userId){
        this.id = userId;
    }

}
