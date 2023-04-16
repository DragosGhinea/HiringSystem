package ro.hiringsystem.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ro.hiringsystem.security.auth.AuthenticationRequest;
import ro.hiringsystem.security.auth.AuthenticationResponse;
import ro.hiringsystem.security.auth.RegisterRequest;

import java.io.IOException;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
