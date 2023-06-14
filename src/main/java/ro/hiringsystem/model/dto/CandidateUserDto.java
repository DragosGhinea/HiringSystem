package ro.hiringsystem.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.hiringsystem.model.auxiliary.CV;

import java.io.File;
import java.net.URL;
import java.util.List;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateUserDto extends UserDto {

    @JsonIgnore
    private CV cv;

    private File recommendation;

    private URL githubProfileLink;

    private URL linkedInProfileLink;

    @JsonIgnore
    private List<JobApplicationDto> jobApplications;

}
