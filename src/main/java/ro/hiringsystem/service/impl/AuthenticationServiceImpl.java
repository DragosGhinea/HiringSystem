package ro.hiringsystem.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.hiringsystem.model.auxiliary.CV;
import ro.hiringsystem.model.dto.CandidateUserDto;
import ro.hiringsystem.model.dto.UserDto;
import ro.hiringsystem.security.JwtService;
import ro.hiringsystem.security.auth.AuthenticationRequest;
import ro.hiringsystem.security.auth.AuthenticationResponse;
import ro.hiringsystem.security.auth.RegisterRequest;
import ro.hiringsystem.security.token.Token;
import ro.hiringsystem.security.token.TokenRepository;
import ro.hiringsystem.security.token.TokenType;
import ro.hiringsystem.service.AuthenticationService;
import ro.hiringsystem.service.EmailSenderService;
import ro.hiringsystem.service.UserMapperService;
import ro.hiringsystem.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final TokenRepository tokenRepository;

    private final UserService<UserDto> userService;

    private final UserMapperService userMapper;

    private final EmailSenderService emailSenderService;

    private final Map<UUID, CandidateUserDto> usersAwaitingConfirmation = new ConcurrentHashMap<>();

    @Override
    public void register(RegisterRequest request) {
        try {
            CandidateUserDto candidateUser = CandidateUserDto.builder()
                    .id(UUID.randomUUID())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .primaryEmail(request.getEmail())
                    .mailList(List.of(request.getEmail()))
                    .phoneNumberList(List.of())
                    .birthDate(request.getBirthDate())
                    .cv(new CV())
                    .build();

            emailSenderService.sendAccountConfirmEmail(request.getEmail(), candidateUser.getId().toString());
            usersAwaitingConfirmation.put(candidateUser.getId(), candidateUser);
            //userService.saveElement(candidateUser);

        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    @Override
    public boolean confirmRegister(UUID token) {
        CandidateUserDto candidateUser = usersAwaitingConfirmation.getOrDefault(token, null);
        if (candidateUser == null)
            return false;
        userService.saveElement(candidateUser);
        usersAwaitingConfirmation.remove(token);
        return true;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );


        UserDto user = userService.getByEmail(request.getEmail());

        revokeAllUserTokens(user);

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(UserDto user, String jwtToken) {
        var token = Token.builder()
                .user(userMapper.toEntity(user))
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(UserDto user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        try {
            userEmail = jwtService.extractUsername(refreshToken);
        }catch(ExpiredJwtException | MalformedJwtException e){
            return;
        }
        catch(Exception x){
            x.printStackTrace();
            return;
        }

        if (userEmail != null) {
            var user = userService.getByEmail(userEmail);
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
