package ro.hiringsystem.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JobType {

    INTERNSHIP_ONE_MONTH(1),

    INTERNSHIP_THREE_MONTHS(3),

    INTERNSHIP_SIX_MONTHS(6),

    FULL_TIME(null),

    PART_TIME(null);

    private final Integer nrMonths;
}
