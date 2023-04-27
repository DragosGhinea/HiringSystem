package ro.hiringsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.hiringsystem.model.dto.InterviewerUserDto;
import ro.hiringsystem.model.entity.InterviewerUser;
import ro.hiringsystem.model.enums.InterviewerType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/interviewer")
public class InterviewerUsersController {

    @GetMapping("profile")
    public ResponseEntity<InterviewerUserDto> getInterviewerUser() {

        return ResponseEntity.ok(InterviewerUserDto.builder()
                .id(UUID.randomUUID())
                .firstName("Jane")
                .lastName("Bergshire")
                .primaryEmail("janeberg@gmail.com")
                .password("test")
                .mailList(List.of("janebergshire@yahoo.com", "jane.berghsire@gmail.com"))
                .phoneNumberList(List.of("0724135235", "0735647578"))
                .birthDate(LocalDate.of(1980, 9, 11))
                .interviewerType(InterviewerType.TECHNICAL)
                .professionalBackground("Jane is a software developer with 10 years of experience in web development using languages such as JavaScript, Python, and PHP. She has expertise in front-end development with React and Angular and has worked on enterprise-level applications. She's proficient in agile methodologies and version control tools like Git, with experience in continuous integration and deployment tools. Jane is a strong problem-solver and effective communicator with a passion for learning and keeping up-to-date with the latest technologies.")
                .build());

    }

}
