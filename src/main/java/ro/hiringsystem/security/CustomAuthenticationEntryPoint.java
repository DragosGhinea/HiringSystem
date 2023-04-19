package ro.hiringsystem.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private void sendError401(HttpServletRequest request, HttpServletResponse response, String customMessage) throws IOException {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.UNAUTHORIZED.value());
        errorResponse.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        errorResponse.put("message", customMessage);
        errorResponse.put("path", request.getServletPath());
        errorResponse.put("timestamp", Instant.now().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(errorResponse);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(json);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (authException instanceof BadCredentialsException) {
            sendError401(request, response, "Invalid password!");
            return;
        }
        else if(authException instanceof InternalAuthenticationServiceException) {
            sendError401(request, response, authException.getMessage());
            return;
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
