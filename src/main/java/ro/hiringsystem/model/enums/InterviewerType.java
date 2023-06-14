package ro.hiringsystem.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public enum InterviewerType {

    TECHNICAL("evaluates mostly hard-skills"),

    HR("evaluates mostly soft-skills");

    private final String roleInAssessmentProcess;

}
