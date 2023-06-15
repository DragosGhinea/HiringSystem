package ro.hiringsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.hiringsystem.service.EmailSenderService;

//currently only used for testing
@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailSenderService emailSenderService;

    @PostMapping("/send-email")
    public ResponseEntity<Object> sendEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body
    ){
        emailSenderService.sendBasicEmail("HiringSystem", to, subject, body);
        return ResponseEntity.ok("Done!");
    }
}
