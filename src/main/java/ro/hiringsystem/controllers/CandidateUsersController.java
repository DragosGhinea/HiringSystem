package ro.hiringsystem.controllers;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ro.hiringsystem.model.auxiliary.AcademicExperience;
import ro.hiringsystem.model.auxiliary.CV;
import ro.hiringsystem.model.CandidateUser;
import ro.hiringsystem.model.auxiliary.Project;
import ro.hiringsystem.model.auxiliary.WorkExperience;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
public class CandidateUsersController {

    @GetMapping("/candidate/profile")
    String getUser(Model model) throws MalformedURLException {

        model.addAttribute("user", CandidateUser.builder()
                                                    .id(UUID.randomUUID())
                                                    .firstName("John")
                                                    .lastName("Neumann")
                                                    .mailList(List.of("johnneumann@yahoo.com", "john.neumann@gmail.com"))
                                                    .phoneNumberList(List.of("0761425366", "0725347587"))
                                                    .birthDate(LocalDate.of(2002, 3, 7))
                .githubProfileLink(new URL("https://github.com/UserName"))
                .linkedInProfileLink(new URL("https://www.linkedin.com/user/"))
                .cv(CV.builder()
                        .academicBackground(List.of(AcademicExperience.builder()
                                .startDate(LocalDate.of(2017, 9, 15))
                                .endDate(LocalDate.of(2021, 6, 15))
                                .institution("'Nicolae Balcescu' National College")
                                .specialization("Mathematics and Informatics").build(),
                                AcademicExperience.builder()
                                        .startDate(LocalDate.of(2021, 7, 25))
                                        .endDate(LocalDate.of(2024, 6, 10))
                                        .institution("University of Bucharest")
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
                                .build())
                .build());

        return "candidateProfile";

    }

}
