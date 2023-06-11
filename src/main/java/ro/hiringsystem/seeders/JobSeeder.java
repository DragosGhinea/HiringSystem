package ro.hiringsystem.seeders;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ro.hiringsystem.model.entity.Job;
import ro.hiringsystem.model.enums.JobType;
import ro.hiringsystem.model.enums.Position;
import ro.hiringsystem.repository.JobRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@ConditionalOnProperty(value = "app.data.seeding.enabled", havingValue = "true")
@RequiredArgsConstructor
public class JobSeeder{

    private final JobRepository jobRepository;

    public void seedData() {
        System.out.println("Starting Job seeding...");

        Job job1 = Job.builder()
                .id(UUID.fromString("165d3b65-ba86-41fe-b5b2-e9c67c59f856"))
                .title("Software Developer")
                .description("We are seeking a skilled and motivated Software Developer to join our dynamic team. " +
                        "As a Software Developer, you will play a key role in designing, developing, and maintaining software solutions that meet " +
                        "the needs of our organization. You will collaborate with cross-functional teams to understand requirements, contribute to architectural " +
                        "discussions, and deliver high-quality code that drives our software products to success.")
                .jobType(JobType.FULL_TIME)
                .position(Position.JUNIOR)
                .salary(5200.0d)
                .hoursPerWeek(40)
                .startDate(LocalDate.of(2023, 9, 1))
                .skillsNeeded(List.of("Object-oriented programming", "Relational databases",
                        "Testing methodologies", "Collaboration skills", "Software frameworks"))
                .offers(List.of("Career development support", "Competitive rewards package"))
                .build();

        Job job2 = Job.builder()
                .id(UUID.fromString("164f3b65-cd86-41fe-b5b2-e9c67c59f856"))
                .title("Automation Testing Engineer")
                .description("As a Testing Automation Engineer, you will play a critical role in ensuring the quality and reliability of our software " +
                        "products through the design, implementation, and execution of automated test cases. You will collaborate closely with cross-functional " +
                        "teams, including developers, product managers, and quality assurance professionals, to identify testing requirements and develop robust automation frameworks.")
                .jobType(JobType.INTERNSHIP_THREE_MONTHS)
                .position(Position.INTERN)
                .salary(3260.0d)
                .hoursPerWeek(32)
                .startDate(LocalDate.of(2023, 7, 3))
                .skillsNeeded(List.of("Automation testing expertise",
                        "Proficient programming skills", "Knowledge of testing methodologies",
                        "Strong problem-solving abilities", "Effective collaboration", "Attention to details" +
                        "Continuous learning mindset", "Excellent communication skills"))

                .offers(List.of("Learning opportunities", "Hands-on experience",
                        "Great team", "Professional Development", "Mentorship"))
                .build();

        Job job3 = Job.builder()
                .id(UUID.fromString("164f3b65-cd86-41fe-b5b2-e9c67c59f222"))
                .title("Software Developer for Chip Design & Verification")
                .description("As a Software Developer for Chip Design and Verification, you will be responsible for developing and maintaining software tools, frameworks, and methodologies " +
                        "that support the design, verification, and validation of integrated circuits (ICs) and electronic systems. You will collaborate with hardware engineers, verification engineers, " +
                        "and other stakeholders to design and implement efficient and reliable software solutions that drive the success of our chip design projects.")
                .jobType(JobType.PART_TIME)
                .position(Position.SENIOR)
                .salary(6080.0d)
                .hoursPerWeek(20)
                .startDate(LocalDate.of(2023, 10, 10))
                .skillsNeeded(List.of("Chip design expertise", "Verification methodologies",
                        "Software development", "Collaboration skills", "Problem-solving abilities"))
                .offers(List.of("Flexible work schedule", "Competitive compensation", "Professional development opportunities"))
                .build();

        Job job4 = Job.builder()
                .id(UUID.fromString("174f3b65-cd86-41fe-b5b2-e9c67c59f811"))
                .title("Data Scientist")
                .description("We are seeking a highly skilled and inquisitive Data Scientist to join our " +
                        "dynamic team. As a Data Scientist, you will have a pivotal role in analyzing intricate " +
                        "datasets, devising advanced statistical models, and steering data-driven decision-making processes.")
                .jobType(JobType.PART_TIME)
                .position(Position.SENIOR)
                .salary(4082.0d)
                .hoursPerWeek(30)
                .startDate(LocalDate.of(2023, 7, 30))
                .skillsNeeded(List.of("Data analysis proficiency", "Statistical modeling expertise",
                        "Machine learning", "Effective communication",
                        "Problem-solving skills", "Data visualization abilities"))
                .offers(List.of("Career growth", "Competitive compensation", "Impactful projects",
                        "Collaborative environment", "Flexible program"))
                .build();

        // Save the jobs
        jobRepository.saveAll(Arrays.asList(job1, job2, job3, job4));

        System.out.println("Job seeding completed.");
    }
}
