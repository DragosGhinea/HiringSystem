package ro.hiringsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.hiringsystem.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    //NOT A GOOD PRACTICE TO USE REPOSITORY IN CONTROLLER
    //should be replaced later, used for testing purposes only
    private final UserRepository userRepository;

    @GetMapping("id/{mail}")
    public ResponseEntity<Object> getIdByMail(@PathVariable("mail") String mail){
        Map<String, UUID> map = new HashMap<>();
        map.put("id", userRepository.findIdByEmail(mail));
        return ResponseEntity.ok(map);
    }
}
