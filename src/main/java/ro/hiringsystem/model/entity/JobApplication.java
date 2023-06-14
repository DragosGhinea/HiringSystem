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

    @Column(name = "user_id")
    private UUID candidateUserId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CandidateUser candidateUser;

    @Column(name = "job_id")
    private UUID jobId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "job_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Job job;

    @NonNull
    private LocalDate applicationDate;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Status status;
}
