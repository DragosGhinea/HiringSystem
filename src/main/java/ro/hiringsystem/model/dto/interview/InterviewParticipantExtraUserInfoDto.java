package ro.hiringsystem.model.dto.interview;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class InterviewParticipantExtraUserInfoDto extends InterviewParticipantDto {

    private String firstName;

    private String lastName;

    private String primaryEmail;

    private List<String> mailList;

    private List<String> phoneNumberList;

    private LocalDate birthDate;
}
