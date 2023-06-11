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
public class JobApplicationWithUserDto {

    @JsonProperty("job_application")
    private JobApplicationDto jobApplicationDto;

    @JsonProperty("candidate_user")
    private CandidateUserDto candidateUserDto;
}
