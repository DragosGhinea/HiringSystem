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

import java.net.MalformedURLException;
import java.net.URL;
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
        try {
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
                    .phoneNumberList(Arrays.asList("0747018912"))
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
                    .phoneNumberList(Arrays.asList("0736789899"))
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
                    .phoneNumberList(Arrays.asList("07767891202"))
                    .build();

            // Seed CandidateUsers
            CandidateUser candidate1 = CandidateUser.builder()
                    .id(UUID.fromString("7cddfa88-78c0-4fe6-97c4-2964c634fb77"))
                    .firstName("Alice")
                    .lastName("Johnson")
                    .primaryEmail("alice@example.com")
                    .password(passwordEncoder.encode("password"))
                    .birthDate(LocalDate.of(1990, 5, 15))
                    .githubProfileLink(new URL("https://github.com/alicee"))
                    .linkedInProfileLink(new URL("https://www.linkedin.com/in/alice/"))
                    .cv(CV.builder()
                            .id(UUID.fromString("7cddfa88-78c0-4fe6-97c4-2964c634fb77"))
                            .academicBackground(List.of(AcademicExperience.builder()
                                            .startDate(LocalDate.of(2018, 9, 15))
                                            .endDate(LocalDate.of(2022, 6, 11))
                                            .institution("'Spiru Haret' National College")
                                            .specialization("Mathematics and Informatics").build(),
                                    AcademicExperience.builder()
                                            .startDate(LocalDate.of(2022, 10, 1))
                                            .endDate(LocalDate.of(2025, 6, 30))
                                            .institution("University of Bucharest")
                                            .specialization("Computer Science")
                                            .level("Bachelor Degree").build()))
                            .workExperience(List.of(WorkExperience.builder()
                                            .startDate(LocalDate.of(2022, 7, 3))
                                            .endDate(LocalDate.of(2022, 10, 1))
                                            .company("BCR")
                                            .position("Frontend designer intern").build(),
                                    WorkExperience.builder()
                                            .startDate(LocalDate.of(2022, 10, 1))
                                            .endDate(LocalDate.of(2023, 2, 1))
                                            .company("BCR")
                                            .position("Frontend designer junior").build()))
                            .skills(List.of("ambition", "team-work", "problem-solving", "creative"))
                            .projects(List.of(Project.builder()
                                            .title("Responsive E-commerce Website Redesign")
                                            .description("In this project, I led a team of frontend developers to redesign and enhance an existing " +
                                                    "e-commerce website with a focus on responsiveness and user experience. We implemented a mobile-first " +
                                                    "approach and utilized modern frontend technologies such as HTML5, CSS3, and JavaScript to create a visually " +
                                                    "appealing and user-friendly interface. Our redesign included optimizing the website for various devices and screen " +
                                                    "sizes, improving page load times, and implementing intuitive navigation. Through effective collaboration and attention to detail, " +
                                                    "we successfully transformed the website into a seamless shopping experience for users across multiple platforms.").build(),
                                    Project.builder()
                                            .title("Interactive Travel Blog Website")
                                            .description("For this project, I developed an interactive travel blog website from scratch. The main objective was to provide users with a visually captivating " +
                                                    "platform where they can share their travel experiences and connect with fellow travelers. Using modern frontend technologies such as React.js, HTML5, and CSS3,  " +
                                                    "I created a dynamic and responsive website with interactive features. Users can create personalized profiles, upload travel photos, write blog posts, and engage in "+
                                                    "discussions with other users. The website also incorporates map integration to showcase travel destinations and provides a seamless browsing experience. Through meticulous " +
                                                    "design and development, the travel blog website offers an immersive platform for travel enthusiasts to share their adventures and inspire others to explore the world.").build()))
                            .build()
                    )
                    .mailList(Arrays.asList("alice@example.com"))
                    .phoneNumberList(Arrays.asList("0776789822", "0798122454"))
                    .build();

            CandidateUser candidate2 = CandidateUser.builder()
                    .id(UUID.fromString("f84d7f9b-3961-4dcd-a5c7-1035db184a1a"))
                    .firstName("Bob")
                    .lastName("Smith")
                    .primaryEmail("bob@example.com")
                    .password(passwordEncoder.encode("password"))
                    .birthDate(LocalDate.of(1992, 8, 20))
                    .githubProfileLink(new URL("https://github.com/bobsmh"))
                    .linkedInProfileLink(new URL("https://www.linkedin.com/in/bob/"))
                    .cv(CV.builder()
                            .id(UUID.fromString("f84d7f9b-3961-4dcd-a5c7-1035db184a1a"))
                            .academicBackground(List.of(AcademicExperience.builder()
                                            .startDate(LocalDate.of(2014, 9, 15))
                                            .endDate(LocalDate.of(2018, 6, 11))
                                            .institution("'Tudor Vianu' National College")
                                            .specialization("Mathematics and Informatics").build(),
                                    AcademicExperience.builder()
                                            .startDate(LocalDate.of(2019, 10, 1))
                                            .endDate(LocalDate.of(2022, 6, 30))
                                            .institution("University of Bucharest")
                                            .specialization("Computer Science")
                                            .level("Bachelor Degree").build(),
                                    AcademicExperience.builder()
                                            .startDate(LocalDate.of(2023, 10, 1))
                                            .endDate(LocalDate.of(2026, 7, 1))
                                            .institution("Politehnica University of Bucharest")
                                            .specialization("Computer Science")
                                            .level("Master's Degree").build()))
                            .workExperience(List.of(WorkExperience.builder()
                                            .startDate(LocalDate.of(2022, 7, 3))
                                            .endDate(LocalDate.of(2022, 10, 1))
                                            .company("Endava")
                                            .position("Java intern").build(),
                                    WorkExperience.builder()
                                            .startDate(LocalDate.of(2022, 10, 15))
                                            .endDate(LocalDate.of(2023, 3, 18))
                                            .company("UiPath")
                                            .position("Software developer junior").build()))
                            .skills(List.of("ambition", "team-work", "problem-solving"))
                            .projects(List.of(Project.builder()
                                            .title("Linux System Administration and Automation")
                                            .description("Demonstrating my proficiency in Linux administration and automation, this project showcased my ability to efficiently manage and optimize Linux server environments. " +
                                                    "I implemented system configurations, automated tasks using scripting languages like Bash and Python, and utilized tools like Ansible and Puppet for configuration management. " +
                                                    "By ensuring system stability, security, and high availability, I delivered reliable solutions tailored to Linux-based ecosystems.").build(),
                                    Project.builder()
                                            .title("E-commerce Platform Enhancement")
                                            .description("In this software development project, I played a crucial role in enhancing an existing e-commerce platform. The objective was to improve the user " +
                                                    "experience, optimize performance, and introduce new functionalities. Working closely with the development team, I conducted a comprehensive analysis of the " +
                                                    "platform's pain points and identified areas for improvement. I implemented responsive design principles to ensure the platform is mobile-friendly and accessible across " +
                                                    "different devices. Additionally, I integrated secure payment gateways, implemented advanced search and filtering options, and enhanced the checkout process. Through rigorous " +
                                                    "testing and continuous feedback, we successfully delivered an upgraded e-commerce platform that offered an enhanced shopping experience for users and increased conversions for the business.").build()))
                                .build()
                    )
                    .mailList(Arrays.asList("bob@example.com"))
                    .phoneNumberList(Arrays.asList("0743567821"))
                    .build();

            CandidateUser candidate3 = CandidateUser.builder()
                    .id(UUID.fromString("38e57c39-38b0-4dfb-8f4f-0b8e6b3efbce"))
                    .firstName("Emily")
                    .lastName("Taylor")
                    .primaryEmail("emily@example.com")
                    .password(passwordEncoder.encode("password"))
                    .birthDate(LocalDate.of(1995, 10, 25))
                    .githubProfileLink(new URL("https://github.com/emily"))
                    .linkedInProfileLink(new URL("https://www.linkedin.com/in/emily/"))
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
                                            .specialization("Computer Science")
                                            .level("Bachelor Degree").build(),
                                    AcademicExperience.builder()
                                            .startDate(LocalDate.of(2024, 7, 28))
                                            .endDate(LocalDate.of(2027, 6, 11))
                                            .institution("University of Bucharest")
                                            .specialization("Computer Science")
                                            .level("Master's Degree").build(),
                                    AcademicExperience.builder()
                                            .startDate(LocalDate.of(2027, 7, 25))
                                            .endDate(LocalDate.of(2030, 6, 10))
                                            .institution("University of Zurich")
                                            .specialization("Computer Science")
                                            .level("Doctorate degree").build(),
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
                    .phoneNumberList(Arrays.asList("0775689433"))
                    .build();

            // Save the users
            managerUserRepository.saveAll(Arrays.asList(manager1));
            interviewerUserRepository.saveAll(Arrays.asList(interviewer1, interviewer2));
            candidateUserRepository.saveAll(Arrays.asList(candidate1, candidate2, candidate3));

            System.out.println("User seeding completed.");
        } catch ( MalformedURLException e ) {
            e.printStackTrace();
        }
    }
}
