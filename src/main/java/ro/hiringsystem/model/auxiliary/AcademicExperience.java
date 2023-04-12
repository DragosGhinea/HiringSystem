package ro.hiringsystem.model.auxiliary;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.hiringsystem.model.abstracts.Experience;

import java.time.LocalDate;
import java.util.UUID;

@SuperBuilder
@Getter
@Setter
@Entity
public class AcademicExperience extends Experience {

    private String institution;

    private String specialization;

    private String level;

    public AcademicExperience() {

    }

    public AcademicExperience(LocalDate startDate, LocalDate endDate, String institution, String specialization, String level) {
        super(startDate, endDate);
        this.institution = institution;
        this.specialization = specialization;
        this.level = level;
    }

}
