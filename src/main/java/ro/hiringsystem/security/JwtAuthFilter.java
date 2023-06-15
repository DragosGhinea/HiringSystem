package ro.hiringsystem.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ro.hiringsystem.security.token.TokenRepository;

import java.io.IOException;

/**
 * JwtAuthFilter is a filter component used for JWT authentication in the application.
 * This filter intercepts incoming requests and performs JWT authentication for routes other than "/api/v1/auth".
 */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    /**
     * Performs the JWT authentication logic for each incoming request.
     * If the request is for "/api/v1/auth", the filter chain continues without authentication.
     * If the request contains a valid JWT token, it is used for authentication.
     *
     * @param request     The incoming HTTP request.
     * @param response    The HTTP response.
     * @param filterChain The filter chain.
     * @throws ServletException If an error occurs while processing the request.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Skip authentication for "/api/v1/auth" endpoints
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final String userEmail;
        final String jwtToken;

        // Skip authentication if Authorization header is missing or doesn't start with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT token from the Authorization header
        jwtToken = authHeader.substring(7);
        try {
            // Extract the username from the JWT token
            userEmail = jwtService.extractUsername(jwtToken);
        } catch (ExpiredJwtException | MalformedJwtException e) {
            // If the token is expired or malformed, skip authentication
            filterChain.doFilter(request, response);
            return;
        } catch (Exception x) {
            x.printStackTrace();
            filterChain.doFilter(request, response);
            return;
        }

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details from the UserDetailsService
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            // Check if the token is valid and not expired or revoked
            var isTokenValid = tokenRepository.findByToken(jwtToken)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);

            // Perform authentication if the token is valid
            if (isTokenValid && jwtService.isTokenValid(jwtToken, userDetails)) {
                // Create an authentication token with the user details
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Set the authentication token in the security context
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}

