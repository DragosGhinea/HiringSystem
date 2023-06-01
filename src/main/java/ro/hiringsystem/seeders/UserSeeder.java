package ro.hiringsystem.seeders;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ro.hiringsystem.model.auxiliary.CV;
import ro.hiringsystem.model.entity.CandidateUser;
import ro.hiringsystem.model.entity.InterviewerUser;
import ro.hiringsystem.model.enums.InterviewerType;
import ro.hiringsystem.repository.CandidateUserRepository;
import ro.hiringsystem.repository.InterviewerUserRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

@Component
@ConditionalOnProperty(value = "app.data.seeding.enabled", havingValue = "true")
@RequiredArgsConstructor
public class UserSeeder{

    private final InterviewerUserRepository interviewerUserRepository;
    private final CandidateUserRepository candidateUserRepository;
    private final PasswordEncoder passwordEncoder;

    public void seedData() {
        System.out.println("Starting User seeding...");

        InterviewerUser interviewer1 = InterviewerUser.builder()
                .id(UUID.fromString("825d3b65-ba86-41fe-b5b2-e9c67c59f868"))
                .firstName("John")
                .lastName("Doe")
                .primaryEmail("john@example.com")
                .password(passwordEncoder.encode("password"))
                .birthDate(LocalDate.of(1980, 1, 1))
                .interviewerType(InterviewerType.TECHNICAL)
                .mailList(Arrays.asList("john@example.com"))
                .professionalBackground("Software Engineering")
                .build();

        InterviewerUser interviewer2 = InterviewerUser.builder()
                .id(UUID.fromString("a29c5c47-2b4e-483e-93f3-ae350a22a777"))
                .firstName("Jane")
                .lastName("Smith")
                .primaryEmail("jane@example.com")
                .password(passwordEncoder.encode("password"))
                .birthDate(LocalDate.of(1985, 2, 10))
                .interviewerType(InterviewerType.HR)
                .mailList(Arrays.asList("jane@example.com"))
                .build();

        // Seed CandidateUsers
        CandidateUser candidate1 = CandidateUser.builder()
                .id(UUID.fromString("7cddfa88-78c0-4fe6-97c4-2964c634fb77"))
                .firstName("Alice")
                .lastName("Johnson")
                .primaryEmail("alice@example.com")
                .password(passwordEncoder.encode("password"))
                .birthDate(LocalDate.of(1990, 5, 15))
                .cv(new CV())
                .mailList(Arrays.asList("alice@example.com"))
                .build();

        CandidateUser candidate2 = CandidateUser.builder()
                .id(UUID.fromString("f84d7f9b-3961-4dcd-a5c7-1035db184a1a"))
                .firstName("Bob")
                .lastName("Smith")
                .primaryEmail("bob@example.com")
                .password(passwordEncoder.encode("password"))
                .birthDate(LocalDate.of(1992, 8, 20))
                .cv(new CV())
                .mailList(Arrays.asList("bob@example.com"))
                .build();

        CandidateUser candidate3 = CandidateUser.builder()
                .id(UUID.fromString("38e57c39-38b0-4dfb-8f4f-0b8e6b3efbce"))
                .firstName("Emily")
                .lastName("Taylor")
                .primaryEmail("emily@example.com")
                .password(passwordEncoder.encode("password"))
                .birthDate(LocalDate.of(1995, 10, 25))
                .cv(new CV())
                .mailList(Arrays.asList("emily@example.com"))
                .build();

        // Save the users
        interviewerUserRepository.saveAll(Arrays.asList(interviewer1, interviewer2));
        candidateUserRepository.saveAll(Arrays.asList(candidate1, candidate2, candidate3));

        System.out.println("User seeding completed.");
    }
}
