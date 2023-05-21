package ro.hiringsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.hiringsystem.model.enums.JobType;

@RestController
@RequestMapping("/api/v1/jobTypes")
public class JobTypesController {

    @GetMapping
    public ResponseEntity<JobType[]> getAllJobTypes() {
        JobType[] jobTypes = JobType.values();
        return ResponseEntity.ok(jobTypes);
    }
}