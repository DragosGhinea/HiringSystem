package ro.hiringsystem.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {

    ANONYMOUS,

    CANDIDATE,

    INTERVIEWER,

    MANAGER;

}
