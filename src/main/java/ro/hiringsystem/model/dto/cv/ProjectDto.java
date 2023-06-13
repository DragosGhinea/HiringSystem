package ro.hiringsystem.model.dto.cv;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProjectDto {
    private UUID id;

    private String title;

    private String description;
}
