package ro.hiringsystem.model.auxiliary;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder
@Getter
@Setter
@Entity
public class Project {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    public Project() {

    }

    public Project(String title, String description) {
        this.title = title;
        this.description = description;
    }

}
