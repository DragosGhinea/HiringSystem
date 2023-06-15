package ro.hiringsystem.seeders;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ro.hiringsystem.model.entity.JobApplication;
import ro.hiringsystem.model.enums.Status;
import ro.hiringsystem.repository.JobApplicationRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

@Component
@ConditionalOnProperty(value = "app.data.seeding.enabled", havingValue = "true")
@RequiredArgsConstructor
public class JobApplicationSeeder{

    private final JobApplicationRepository jobApplicationRepository;

    public void seedData() {
        System.out.println("Starting JobApplication seeding...");

        JobApplication jobApplication1 = JobApplication.builder()
                .id(UUID.fromString("123d3b65-ba86-41fe-b5b2-e9c67c59f321"))
                .candidateUserId(UUID.fromString("7cddfa88-78c0-4fe6-97c4-2964c634fb77"))
                .jobId(UUID.fromString("165d3b65-ba86-41fe-b5b2-e9c67c59f856"))
                .applicationDate(LocalDate.of(2023, 6, 9))
                .status(Status.SUBMITTED)
                .build();

        JobApplication jobApplication2 = JobApplication.builder()
                .id(UUID.fromString("123a1a23-ba86-41fe-b5b2-e9c67c59f321"))
                .candidateUserId(UUID.fromString("f84d7f9b-3961-4dcd-a5c7-1035db184a1a"))
                .jobId(UUID.fromString("165d3b65-ba86-41fe-b5b2-e9c67c59f856"))
                .applicationDate(LocalDate.of(2023, 6, 1))
                .status(Status.DENIED)
                .build();

        JobApplication jobApplication3 = JobApplication.builder()
                .id(UUID.fromString("123d3b65-cd01-41fe-b5b2-e9c67c59f100"))
                .candidateUserId(UUID.fromString("38e57c39-38b0-4dfb-8f4f-0b8e6b3efbce"))
                .jobId(UUID.fromString("164f3b65-cd86-41fe-b5b2-e9c67c59f222"))
                .applicationDate(LocalDate.of(2023, 5, 14))
                .status(Status.SUBMITTED)
                .build();

        JobApplication jobApplication4 = JobApplication.builder()
                .id(UUID.fromString("987d3b65-cd01-41fe-b5b2-e9c67c59f199"))
                .candidateUserId(UUID.fromString("38e57c39-38b0-4dfb-8f4f-0b8e6b3efbce"))
                .jobId(UUID.fromString("174f3b65-cd86-41fe-b5b2-e9c67c59f811"))
                .applicationDate(LocalDate.of(2023, 6, 18))
                .status(Status.SUBMITTED)
                .build();

        JobApplication jobApplication5 = JobApplication.builder()
                .id(UUID.fromString("987d3b65-cd33-33fe-b5b2-e9c67c59f199"))
                .candidateUserId(UUID.fromString("7cddfa88-78c0-4fe6-97c4-2964c634fb77"))
                .jobId(UUID.fromString("164f3b65-cd86-41fe-b5b2-e9c67c59f856"))
                .applicationDate(LocalDate.of(2023, 4, 1))
                .status(Status.SUBMITTED)
                .build();

        // Save the jobs applications
        jobApplicationRepository.saveAll(Arrays.asList(jobApplication1, jobApplication2, jobApplication3, jobApplication4, jobApplication5));

        System.out.println("JobApplication seeding completed.");
    }
}
