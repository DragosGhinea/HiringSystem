package ro.hiringsystem.controller;

import jakarta.persistence.ElementCollection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ro.hiringsystem.model.InterviewerUser;
import ro.hiringsystem.model.Job;
import ro.hiringsystem.model.enums.InterviewerType;
import ro.hiringsystem.model.enums.JobType;
import ro.hiringsystem.model.enums.Position;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Controller
public class JobsController {
    @GetMapping("/job/display")
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
}
