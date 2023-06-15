package ro.hiringsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ro.hiringsystem.model.dto.JobApplicationDto;
import ro.hiringsystem.model.dto.UserDto;
import ro.hiringsystem.service.CandidateUserService;
import ro.hiringsystem.service.EmailSenderService;
import ro.hiringsystem.service.JobApplicationService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/application")
@RequiredArgsConstructor

public class JobApplicationsController {

    private final JobApplicationService jobApplicationService;

    private final EmailSenderService emailSenderService;

    private final CandidateUserService candidateUserService;

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

    @GetMapping("/check/{id}")
    public ResponseEntity<Object> checkIfAlreadyApplied(@PathVariable("id") UUID jobId, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            UserDto userDto = (UserDto) authentication.getPrincipal();

            UUID candidateUserId = userDto.getId();

            return ResponseEntity.ok(jobApplicationService.checkIfAlreadyApplied(jobId, candidateUserId));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/apply/{id}")
    public ResponseEntity<Object> applyToJob(@PathVariable("id") UUID jobId, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            UserDto userDto = (UserDto) authentication.getPrincipal();

            UUID candidateUserId = userDto.getId();

            return ResponseEntity.ok(jobApplicationService.create(jobId, candidateUserId));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/get/all/{id}")
    public ResponseEntity<Object> getAllByJobId(@PathVariable("id") UUID jobId) {
        return ResponseEntity.ok(jobApplicationService.getAllByJobId(jobId));
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

    @PostMapping("/status/update/{status}/{id}")
    public ResponseEntity<Object> updateStatus(@PathVariable("status") String status, @PathVariable("id") UUID id){
        if(status.equalsIgnoreCase("ACCEPTED")) {
            emailSenderService.sendApplicationAcceptedEmail(candidateUserService.getById(jobApplicationService.getById(id).getCandidateUserId()).getPrimaryEmail());
            return ResponseEntity.ok(jobApplicationService.accept(id));
        }
        else if(status.equalsIgnoreCase("DENIED")) {
            emailSenderService.sendDenyApplicationEmail(candidateUserService.getById(jobApplicationService.getById(id).getCandidateUserId()).getPrimaryEmail());
            return ResponseEntity.ok(jobApplicationService.reject(id));
        }
        else
            return ResponseEntity.badRequest().build();
    }
}
