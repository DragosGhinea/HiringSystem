package ro.hiringsystem.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public enum InterviewerType {

    TECHNICAL("technical"),

    HR("hr"),

    NONE("none");

    private final String typeString;

}
