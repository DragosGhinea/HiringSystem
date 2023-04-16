package ro.hiringsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.hiringsystem.model.enums.Status;

import java.time.LocalDate;
import java.util.UUID;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationDto {

    private UUID id;

    private UUID candidateUserId;

    private UUID jobId;

    private LocalDate applicationDate;

    private Status status;
}
