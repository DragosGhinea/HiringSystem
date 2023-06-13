package ro.hiringsystem.model.dto.cv;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class WorkExperienceDto {

    private UUID id;

    private LocalDate startDate;

    private LocalDate endDate;

    private String company;

    private String position;
}
