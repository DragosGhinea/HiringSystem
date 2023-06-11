package ro.hiringsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.hiringsystem.model.dto.InterviewerUserDto;
import ro.hiringsystem.model.enums.InterviewerType;
import ro.hiringsystem.service.InterviewerUserService;

import java.util.Arrays;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/interviewer")
@RequiredArgsConstructor
public class InterviewerUsersController {
    private final InterviewerUserService interviewerUserService;

    @GetMapping("profile/{id}")
    public ResponseEntity<InterviewerUserDto> getInterviewerUser(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(interviewerUserService.getById(id));
    }

    @PostMapping("create")
    public ResponseEntity<InterviewerUserDto> createInterviewerUser(@RequestBody InterviewerUserDto interviewerUserDto){
        return ResponseEntity.ok(interviewerUserService.create(interviewerUserDto));
    }

    @GetMapping("/types")
    public ResponseEntity<InterviewerType[]> getAllInterviewerTypes() {
        InterviewerType[] selectedTypes = Arrays.stream(InterviewerType.values())
                .toArray(InterviewerType[]::new);

        return ResponseEntity.ok(selectedTypes);
    }
}
