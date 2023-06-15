package ro.hiringsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ro.hiringsystem.model.dto.InterviewerUserDto;
import ro.hiringsystem.model.enums.InterviewerType;
import ro.hiringsystem.service.InterviewerUserService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/interviewer")
@RequiredArgsConstructor
public class InterviewerUsersController {
    private final InterviewerUserService interviewerUserService;

    @GetMapping("get/all/paginated")
    public ResponseEntity<List<InterviewerUserDto>> getAllCandidateUsersPaginated(@RequestParam("page") int page, @RequestParam("size") int size) {
        if(page <= 0)
            page = 1;
        return ResponseEntity.ok(interviewerUserService.getAll(page-1, size));
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<Void> deleteCandidateUser(@PathVariable("id") UUID id) {
        interviewerUserService.removeElementById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("profile/{id}")
    public ResponseEntity<InterviewerUserDto> getInterviewerUser(@PathVariable("id") String id, Authentication authentication) {
        if(authentication == null || !authentication.isAuthenticated())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if(id.equals("me"))
            return ResponseEntity.ok((InterviewerUserDto) authentication.getPrincipal());
        else
            return ResponseEntity.ok(interviewerUserService.getById(UUID.fromString(id)));
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
