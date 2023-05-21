package ro.hiringsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.hiringsystem.model.dto.JobDto;
import ro.hiringsystem.model.dto.responses.CreateEditJobResponse;
import ro.hiringsystem.model.entity.Job;
import ro.hiringsystem.model.enums.JobType;
import ro.hiringsystem.model.enums.Position;
import ro.hiringsystem.service.JobService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class JobsController {

    private final JobService jobService;

    @GetMapping("/job/display")
    String getUser(Model model) {

        model.addAttribute("job", Job.builder()
                .id(UUID.randomUUID())
                .title("Software developer")
                .description("Our team is high-talent and high-energy, creating new technologies that must achieve the best protection in the marketplace. Our Trainees will develop independent modules or products, working closely with our Senior Developers. The aim is to develop practical designs and then implement them, going through all the stages of a research project, including a public release")
                .jobType(JobType.INTERNSHIP_ONE_MONTH)
                .position(Position.INTERN)
                .salary(4200.0)
                .hoursPerWeek(8)
                .startDate(LocalDate.of(2023, 7, 1))
                .skillsNeeded(List.of("Basic OOP knowledge", "Team management"))
                .offers(List.of("Great trainees", "New technologies"))
                .build());

        return "jobDisplay";

    }

    @GetMapping("/job/get")
    public ResponseEntity<JobDto> get (@RequestParam(required = true) UUID id) {
        return ResponseEntity.ok(jobService.getById(id));
    }

    @PostMapping("/job/create")
    public ResponseEntity<CreateEditJobResponse> create (@RequestBody JobDto jobDto) {
        return ResponseEntity.ok(jobService.createEdit(jobDto));
    }

    @PostMapping("/job/edit")
    public ResponseEntity<CreateEditJobResponse> edit(
            @RequestParam(required = true) UUID id,
            @RequestBody JobDto jobDto
    ) {
        jobDto.setId(id);
        return ResponseEntity.ok(jobService.createEdit(jobDto));
    }

    @PostMapping("/job/delete")
    public ResponseEntity<CreateEditJobResponse> delete(@RequestParam(required = true) UUID id) {
        jobService.removeElementById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/jobTypes")
    public ResponseEntity<JobType[]> getAllJobTypes() {
        JobType[] jobTypes = JobType.values();
        return ResponseEntity.ok(jobTypes);
    }

    @GetMapping("/positions")
    public ResponseEntity<Position[]> getAllPositions() {
        Position[] positions = Position.values();
        return ResponseEntity.ok(positions);
    }
}
