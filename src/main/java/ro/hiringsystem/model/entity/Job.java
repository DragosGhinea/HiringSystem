package ro.hiringsystem.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.hiringsystem.model.enums.JobType;
import ro.hiringsystem.model.enums.Position;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="JOB")
public class Job {
    @Id
    private UUID id;

    @NonNull
    private String title;

    @Column(columnDefinition = "TEXT")
    @NonNull
    private String description;

    @NonNull
    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Position position;

    private Double salary;

    private Integer hoursPerWeek;

    private LocalDate startDate;

    @ElementCollection(fetch=FetchType.EAGER)
    private List<String> skillsNeeded;

    @ElementCollection(fetch=FetchType.EAGER)
    private List<String> offers;

    @OneToMany(mappedBy = "jobId", fetch=FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<JobApplication> jobApplications;
}
