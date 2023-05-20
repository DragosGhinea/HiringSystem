package ro.hiringsystem.model.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.hiringsystem.model.enums.JobType;
import ro.hiringsystem.model.enums.Position;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateEditJobResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("job_type")
    private JobType jobType;

    @JsonProperty("position")
    private Position position;

    @JsonProperty("salary")
    private Double salary;

    @JsonProperty("hours_per_week")
    private Integer hoursPerWeek;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("skills_needed")
    private List<String> skillsNeeded;

    @JsonProperty("offers")
    private List<String> offers;
}
