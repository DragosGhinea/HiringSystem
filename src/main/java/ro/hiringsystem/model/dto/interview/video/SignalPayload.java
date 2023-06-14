package ro.hiringsystem.model.dto.interview.video;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ro.hiringsystem.model.dto.interview.InterviewParticipantExtraUserInfoDto;

@Getter
@Setter
public class SignalPayload {
    private String id;
    private Object signal;
    private String callerID;
    private String userToSignal;

    @JsonProperty("extraUserInfo")
    private InterviewParticipantExtraUserInfoDto extraUserInfoDto;
}
