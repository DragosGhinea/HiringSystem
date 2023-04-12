package ro.hiringsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.hiringsystem.model.abstracts.User;
import ro.hiringsystem.model.dto.UserDto;
import ro.hiringsystem.service.UserService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService<UserDto> userService;


    @GetMapping("/users/get/{id}")
    public UserDto getUserById(UUID id) {
        return userService.getById(id);
    }

    @GetMapping("/users/get/{firstName}")
    public UserDto getUserByFirstName(String firstName) {
        return userService.getUserByFirstName(firstName);
    }
}
