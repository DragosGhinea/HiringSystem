package ro.hiringsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ro.hiringsystem.model.dto.JobApplicationDto;
import ro.hiringsystem.model.dto.UserDto;
import ro.hiringsystem.service.JobApplicationService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/application")
@RequiredArgsConstructor

public class JobApplicationsController {

    private final JobApplicationService jobApplicationService;

    @PostMapping("/create")
    public ResponseEntity<JobApplicationDto> create (@RequestParam(required = true) UUID jobId, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok(jobApplicationService.create(jobId, ((UserDto) authentication.getPrincipal()).getId()));
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<JobApplicationDto> delete (@RequestParam(required = true) UUID jobApplicationId) {
        jobApplicationService.removeElementById(jobApplicationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/all/user/{userId}")
    public ResponseEntity<Object> getApplications(@PathVariable UUID userId){
        return ResponseEntity.ok(jobApplicationService.getAllByUserId(userId));
    }

    @GetMapping("/get/all/my")
    public ResponseEntity<Object> getMyApplications(Authentication authentication){
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok(jobApplicationService.getAllByUserId(((UserDto) authentication.getPrincipal()).getId()));
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
