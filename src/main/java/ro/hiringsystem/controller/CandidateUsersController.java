package ro.hiringsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.hiringsystem.model.dto.CandidateUserDto;
import ro.hiringsystem.model.dto.cv.CVDto;
import ro.hiringsystem.service.CandidateUserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/candidate")
@RequiredArgsConstructor
public class CandidateUsersController {
    private final CandidateUserService candidateUserService;

    @PostMapping("delete/{id}")
    public ResponseEntity<Void> deleteCandidateUser(@PathVariable("id") UUID id) {
        candidateUserService.removeElementById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("get/all/paginated")
    public ResponseEntity<List<CandidateUserDto>> getAllCandidateUsersPaginated(@RequestParam("page") int page, @RequestParam("size") int size) {
        if(page <= 0)
            page = 1;
        return ResponseEntity.ok(candidateUserService.getAll(page-1, size));
    }

    @GetMapping("profile/{id}")
    public ResponseEntity<CandidateUserDto> getCandidateUser(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(candidateUserService.getById(id));

        /*
        return ResponseEntity.ok(CandidateUserDto.builder()
                                                    .id(UUID.randomUUID())
                                                    .firstName("John")
                                                    .lastName("Neumann")
                                                    .mailList(List.of("johnneumann@yahoo.com", "john.neumann@gmail.com"))
                                                    .phoneNumberList(List.of("0761425366", "0725347587"))
                                                    .birthDate(LocalDate.of(2002, 3, 7))
                .githubProfileLink(new URL("https://github.com/UserName"))
                .linkedInProfileLink(new URL("https://www.linkedin.com/in/user/"))
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
                                .build())
                .primaryEmail("test")
                .password("test")
                .build());
        */
    }

    @GetMapping(value="get/cv/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CVDto> getCandidateUserCV(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(candidateUserService.getUserCV(id));
    }

    @PostMapping("/create")
    public ResponseEntity<CandidateUserDto> register(@RequestBody CandidateUserDto candidateUserDto){
        return ResponseEntity.ok(candidateUserService.create(candidateUserDto));
    }

}
