package ro.hiringsystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.hiringsystem.security.auth.AuthenticationRequest;
import ro.hiringsystem.security.auth.AuthenticationResponse;
import ro.hiringsystem.security.auth.RegisterRequest;
import ro.hiringsystem.service.AuthenticationService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody RegisterRequest request
    ){
        authService.register(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register/confirm/{token}")
    public ResponseEntity<Boolean> confirmRegister(
            @PathVariable UUID token
    ){
        return ResponseEntity.ok(authService.confirmRegister(token));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authService.refreshToken(request, response);
    }
}
