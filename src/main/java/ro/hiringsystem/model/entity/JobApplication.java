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
    private UUID id;

//    @ManyToOne
//    @JoinColumn(name = "candidate_id")
//    private CandidateUser candidateUser;

    private UUID candidateUserId;

    // @ManyToOne
    // @JoinColumn(name = "job_id")
    //private Job job;

    private UUID jobId;

    @NonNull
    private LocalDate applicationDate;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Status status;
}
