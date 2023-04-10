package ro.hiringsystem.model.auxiliary;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.hiringsystem.model.abstracts.Experience;

@SuperBuilder
@Getter
@Setter
public class AcademicExperience extends Experience {

    private String institution;

    private String specialization;

    private String level;

}
