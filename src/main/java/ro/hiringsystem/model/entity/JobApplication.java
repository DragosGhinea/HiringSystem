package ro.hiringsystem.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.hiringsystem.model.enums.Status;

import java.time.LocalDate;
import java.util.UUID;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="JOB_APPLICATION")
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

    @NonNull
    private LocalDate applicationDate;

    @NonNull
    private Status status;
}
