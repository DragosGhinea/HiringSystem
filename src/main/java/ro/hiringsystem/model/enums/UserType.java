package ro.hiringsystem.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {

    ANONYMOUS("anonymous"),

    CANDIDATE("candidate"),

    INTERVIEWER("interviewer"),

    MANAGER("manager"),

    NONE("none");

    private final String typeString;

}
