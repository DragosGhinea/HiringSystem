package ro.hiringsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import ro.hiringsystem.model.enums.JobType;
import ro.hiringsystem.model.enums.Position;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="\"JOB\"")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private JobType jobType;

    @NotNull
    private Position position;

    private Double salary;

    private Integer hoursPerWeek;

    private LocalDate startDate;

    private Period period;

    @ElementCollection
    private List<String> skillsNeeded;

    @ElementCollection
    private List<String> offers;

    @OneToMany(mappedBy = "job")
    private List<JobApplication> jobApplications;
}
