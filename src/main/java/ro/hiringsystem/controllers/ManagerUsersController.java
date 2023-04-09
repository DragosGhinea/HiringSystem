package ro.hiringsystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ro.hiringsystem.model.InterviewerUser;
import ro.hiringsystem.model.ManagerUser;
import ro.hiringsystem.model.enums.InterviewerType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
public class ManagerUsersController {

    @GetMapping("/manager/profile")
    String getUser(Model model) {

        model.addAttribute("user", ManagerUser.builder()
                .id(UUID.randomUUID())
                .firstName("Tom")
                .lastName("Scott")
                .mailList(List.of("tomscott@yahoo.com", "tom.scott@gmail.com"))
                .phoneNumberList(List.of("0724143235", "0735877578"))
                .birthDate(LocalDate.of(1975, 7, 4))
                .professionalBackground("Tom is a seasoned IT manager with over 10 years of experience leading and managing teams in various IT companies. He has a Bachelor's degree in Computer Science and started his career as a software developer. Tom has expertise in managing complex IT projects, developing and implementing IT strategies, and leading teams of developers, designers, and other IT professionals. He has strong leadership, communication, and technical skills, and is proficient in agile methodologies and project management tools. Tom leverages his knowledge of the latest industry trends and technologies to improve the performance of his company.").build());

        return "managerProfile";

    }

}
