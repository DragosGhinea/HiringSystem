package ro.hiringsystem.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationWithJobDto {

    @JsonProperty("job_application")
    private JobApplicationDto jobApplicationDto;

    @JsonProperty("job")
    private JobDto jobDto;
}
