package ro.hiringsystem.model.auxiliary;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.hiringsystem.model.abstracts.Experience;

@SuperBuilder
@Getter
@Setter
public class WorkExperience extends Experience {

    private String company;

    private String position;

}
