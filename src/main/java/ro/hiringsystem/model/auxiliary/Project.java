package ro.hiringsystem.model.auxiliary;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class Project {

    private String title;

    private String description;

}
