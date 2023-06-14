package ro.hiringsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.hiringsystem.model.enums.JobType;
import ro.hiringsystem.model.enums.Position;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class JobDto {
    private UUID id;

    private String title;

    private String description;

    private JobType jobType;

    private Position position;

    private Double salary;

    private Integer hoursPerWeek;

    private LocalDate startDate;

    private List<String> skillsNeeded;

    private List<String> offers;

    private List<JobApplicationDto> jobApplications;
}
