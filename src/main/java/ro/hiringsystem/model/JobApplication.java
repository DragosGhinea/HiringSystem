package ro.hiringsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import ro.hiringsystem.model.enums.Status;

import java.time.LocalDate;
import java.util.UUID;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="\"JOB_APPLICATION\"")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private CandidateUser candidateUser;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @NotNull
    private LocalDate applicationDate;

    @NotNull
    private Status status;
}
