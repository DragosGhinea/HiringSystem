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
public class WorkExperience extends Experience {

    private String company;

    private String position;

    public WorkExperience() {

    }

    public WorkExperience(LocalDate startDate, LocalDate endDate, String company, String position) {
        super(startDate, endDate);
        this.company = company;
        this.position = position;
    }

}
