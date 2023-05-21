package ro.hiringsystem.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum JobType {

    INTERNSHIP_ONE_MONTH(1),

    INTERNSHIP_THREE_MONTHS(3),

    INTERNSHIP_SIX_MONTHS(6),

    FULLTIME(null),

    PARTTIME(null);

    private final Integer nrMonths;
}
