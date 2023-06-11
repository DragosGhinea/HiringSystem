package ro.hiringsystem.seeders;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ro.hiringsystem.model.auxiliary.AcademicExperience;
import ro.hiringsystem.model.auxiliary.CV;
import ro.hiringsystem.model.auxiliary.Project;
import ro.hiringsystem.model.auxiliary.WorkExperience;
import ro.hiringsystem.model.entity.CandidateUser;
import ro.hiringsystem.model.entity.InterviewerUser;
import ro.hiringsystem.model.entity.ManagerUser;
import ro.hiringsystem.model.enums.InterviewerType;
import ro.hiringsystem.repository.CandidateUserRepository;
import ro.hiringsystem.repository.InterviewerUserRepository;
import ro.hiringsystem.repository.ManagerUserRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@ConditionalOnProperty(value = "app.data.seeding.enabled", havingValue = "true")
@RequiredArgsConstructor
public class UserSeeder{

    private final InterviewerUserRepository interviewerUserRepository;
    private final CandidateUserRepository candidateUserRepository;
    private final ManagerUserRepository managerUserRepository;
    private final PasswordEncoder passwordEncoder;

    public void seedData() {
        System.out.println("Starting User seeding...");

        // Seed ManagerUsers
        ManagerUser manager1 = ManagerUser.builder()
                .id(UUID.fromString("b29c5c47-2b4e-483e-93f3-ae350a22a799"))
                .firstName("Mark")
                .lastName("James")
                .primaryEmail("mark@example.com")
                .password(passwordEncoder.encode("password"))
                .birthDate(LocalDate.of(1995, 3, 22))
                .mailList(Arrays.asList("mark@example.com"))
                .build();

        // Seed InterviewerUser
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
                .cv(new CV(UUID.fromString("7cddfa88-78c0-4fe6-97c4-2964c634fb77")))
                .mailList(Arrays.asList("alice@example.com"))
                .build();

        CandidateUser candidate2 = CandidateUser.builder()
                .id(UUID.fromString("f84d7f9b-3961-4dcd-a5c7-1035db184a1a"))
                .firstName("Bob")
                .lastName("Smith")
                .primaryEmail("bob@example.com")
                .password(passwordEncoder.encode("password"))
                .birthDate(LocalDate.of(1992, 8, 20))
                .cv(new CV(UUID.fromString("f84d7f9b-3961-4dcd-a5c7-1035db184a1a")))
                .mailList(Arrays.asList("bob@example.com"))
                .build();

        CandidateUser candidate3 = CandidateUser.builder()
                .id(UUID.fromString("38e57c39-38b0-4dfb-8f4f-0b8e6b3efbce"))
                .firstName("Emily")
                .lastName("Taylor")
                .primaryEmail("emily@example.com")
                .password(passwordEncoder.encode("password"))
                .birthDate(LocalDate.of(1995, 10, 25))
                .cv(CV.builder()
                        .id(UUID.fromString("38e57c39-38b0-4dfb-8f4f-0b8e6b3efbce"))
                        .academicBackground(List.of(AcademicExperience.builder()
                                        .startDate(LocalDate.of(2017, 9, 15))
                                        .endDate(LocalDate.of(2021, 6, 15))
                                        .institution("'Nicolae Balcescu' National College")
                                        .specialization("Mathematics and Informatics").build(),
                                AcademicExperience.builder()
                                        .startDate(LocalDate.of(2021, 7, 25))
                                        .endDate(LocalDate.of(2024, 6, 10))
                                        .institution("University of Bucharest")
                                        .specialization("Computer Science").build(),
                                AcademicExperience.builder()
                                        .startDate(LocalDate.of(2024, 7, 28))
                                        .endDate(LocalDate.of(2027, 6, 11))
                                        .institution("University of Bucharest")
                                        .specialization("Computer Science").build(),
                                AcademicExperience.builder()
                                        .startDate(LocalDate.of(2027, 7, 25))
                                        .endDate(LocalDate.of(2030, 6, 10))
                                        .institution("University of Zurich")
                                        .specialization("Computer Science").build(),
                                AcademicExperience.builder()
                                        .startDate(LocalDate.of(2030, 7, 25))
                                        .endDate(LocalDate.of(2032, 6, 10))
                                        .institution("University of Chicago")
                                        .specialization("Computer Science").build()))
                        .workExperience(List.of(WorkExperience.builder()
                                        .startDate(LocalDate.of(2022, 6, 12))
                                        .endDate(LocalDate.of(2022, 9, 15))
                                        .company("Google")
                                        .position("Java intern").build(),
                                WorkExperience.builder()
                                        .startDate(LocalDate.of(2023, 6, 15))
                                        .endDate(LocalDate.of(2023, 9, 18))
                                        .company("Amazon")
                                        .position("Java junior").build()))
                        .skills(List.of("perseverence", "team-work", "problem-solving"))
                        .projects(List.of(Project.builder()
                                        .title("Sorting algorithms comparison")
                                        .description("The purpose of this project was to get a better idea of the performance of some sorting algorithms" +
                                                " (RadixSort, MergeSort, ShellSort, QuickSort, HeapSort) and the situations when some of them are better than others.").build(),
                                Project.builder()
                                        .title("OOP - Complex Matrix Project")
                                        .description("In the first part of the project, I implemented a class for matrixes with complex elements (as a friend class of the class for complex numbers)," +
                                                " overloaded operators and created public methods for the specific matrix operations. As a follow-up, I created a derived class for square matrixes, and used virtual functions, static variables and functions, to implement some operations with both regular and square matrixes.").build(),
                                Project.builder()
                                        .title("ASP.NET Web Development Project")
                                        .description("This is a team project I made for the Web Development course, using the MVC-Architecture of ASP.NET framework, and the Entity Framework technology. For the authentication system, I used the Identity component. The general strategy I used for the implementation is Code-First.").build()))
                        .build()
                )
                .mailList(Arrays.asList("emily@example.com"))
                .build();

        // Save the users
        managerUserRepository.saveAll(Arrays.asList(manager1));
        interviewerUserRepository.saveAll(Arrays.asList(interviewer1, interviewer2));
        candidateUserRepository.saveAll(Arrays.asList(candidate1, candidate2, candidate3));

        System.out.println("User seeding completed.");
    }
}
