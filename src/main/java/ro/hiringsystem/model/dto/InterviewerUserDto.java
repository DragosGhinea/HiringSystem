package ro.hiringsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.hiringsystem.model.abstracts.User;
import ro.hiringsystem.model.enums.InterviewerType;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewerUserDto extends UserDto {

    private InterviewerType interviewerType;

    private String professionalBackground;

}
