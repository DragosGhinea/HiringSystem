package ro.hiringsystem.model.dto.interview.video;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignalPayload {
    private String id;
    private Object signal;
    private String callerID;
    private String userToSignal;
}
