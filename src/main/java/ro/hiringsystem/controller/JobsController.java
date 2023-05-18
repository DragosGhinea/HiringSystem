package ro.hiringsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.hiringsystem.model.dto.JobDto;
import ro.hiringsystem.model.dto.responses.CreateJobResponse;
import ro.hiringsystem.model.entity.Job;
import ro.hiringsystem.model.enums.JobType;
import ro.hiringsystem.model.enums.Position;
import ro.hiringsystem.security.auth.AuthenticationResponse;
import ro.hiringsystem.security.auth.RegisterRequest;
import ro.hiringsystem.service.AuthenticationService;
import ro.hiringsystem.service.JobService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/job")
@RequiredArgsConstructor
public class JobsController {

    private final JobService jobService;

    @GetMapping("/display")
    String getUser(Model model) {

        model.addAttribute("job", Job.builder()
                .id(UUID.randomUUID())
                .title("Software developer")
                .description("Our team is high-talent and high-energy, creating new technologies that must achieve the best protection in the marketplace. Our Trainees will develop independent modules or products, working closely with our Senior Developers. The aim is to develop practical designs and then implement them, going through all the stages of a research project, including a public release")
                .jobType(JobType.INTERNSHIP)
                .position(Position.INTERN)
                .salary(4200.0)
                .hoursPerWeek(8)
                .startDate(LocalDate.of(2023, 7, 1))
                .period(Period.of(0, 3, 0))
                .skillsNeeded(List.of("Basic OOP knowledge", "Team management"))
                .offers(List.of("Great trainees", "New technologies"))
                .build());

        return "jobDisplay";

    }

    @PostMapping("create")
    public ResponseEntity<CreateJobResponse> create(
            @RequestBody JobDto jobDto
    ){
        System.out.println("se creeaza jobul");
        return ResponseEntity.ok(jobService.create(jobDto));
    }

}
