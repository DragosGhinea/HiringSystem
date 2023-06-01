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
import ro.hiringsystem.model.auxiliary.AcademicExperience;
import ro.hiringsystem.model.auxiliary.CV;
import ro.hiringsystem.model.auxiliary.Project;
import ro.hiringsystem.model.auxiliary.WorkExperience;
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
import ro.hiringsystem.service.UserMapperService;
import ro.hiringsystem.service.UserService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final TokenRepository tokenRepository;

    private final UserService<UserDto> userService;

    private final UserMapperService userMapper;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {

        //code to generate user required here
        //using a manual user for testing purposes
        //taken from CandidateUsersController

        try {
            CandidateUserDto candidateUser = CandidateUserDto.builder()
                    .id(UUID.randomUUID())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .primaryEmail(request.getEmail())
                    .mailList(List.of(request.getEmail()))
                    .phoneNumberList(List.of("0761425366", "0725347587"))
                    .birthDate(LocalDate.of(2002, 3, 7))
                    .githubProfileLink(new URL("https://github.com/UserName"))
                    .linkedInProfileLink(new URL("https://www.linkedin.com/user/"))
                    .cv(CV.builder()
                            .academicBackground(List.of(AcademicExperience.builder()
                                            .startDate(LocalDate.of(2017, 9, 15))
                                            .endDate(LocalDate.of(2021, 6, 15))
                                            .institution("'Nicolae Balcescu' National College")
                                            .specialization("Mathematics and Informatics").build(),
                                    AcademicExperience.builder()
                                            .startDate(LocalDate.of(2021, 7, 25))
                                            .endDate(LocalDate.of(2024, 6, 10))
                                            .institution("University of Bucharest")
                                            .specialization("Computer Science").build(),
                                    AcademicExperience.builder()
                                            .startDate(LocalDate.of(2024, 7, 28))
                                            .endDate(LocalDate.of(2027, 6, 11))
                                            .institution("University of Bucharest")
                                            .specialization("Computer Science").build(),
                                    AcademicExperience.builder()
                                            .startDate(LocalDate.of(2027, 7, 25))
                                            .endDate(LocalDate.of(2030, 6, 10))
                                            .institution("University of Zurich")
                                            .specialization("Computer Science").build(),
                                    AcademicExperience.builder()
                                            .startDate(LocalDate.of(2030, 7, 25))
                                            .endDate(LocalDate.of(2032, 6, 10))
                                            .institution("University of Chicago")
                                            .specialization("Computer Science").build()))
                            .workExperience(List.of(WorkExperience.builder()
                                            .startDate(LocalDate.of(2022, 6, 12))
                                            .endDate(LocalDate.of(2022, 9, 15))
                                            .company("Google")
                                            .position("Java intern").build(),
                                    WorkExperience.builder()
                                            .startDate(LocalDate.of(2023, 6, 15))
                                            .endDate(LocalDate.of(2023, 9, 18))
                                            .company("Amazon")
                                            .position("Java junior").build()))
                            .skills(List.of("perseverence", "team-work", "problem-solving"))
                            .projects(List.of(Project.builder()
                                            .title("Sorting algorithms comparison")
                                            .description("The purpose of this project was to get a better idea of the performance of some sorting algorithms" +
                                                    " (RadixSort, MergeSort, ShellSort, QuickSort, HeapSort) and the situations when some of them are better than others.").build(),
                                    Project.builder()
                                            .title("OOP - Complex Matrix Project")
                                            .description("In the first part of the project, I implemented a class for matrixes with complex elements (as a friend class of the class for complex numbers)," +
                                                    " overloaded operators and created public methods for the specific matrix operations. As a follow-up, I created a derived class for square matrixes, and used virtual functions, static variables and functions, to implement some operations with both regular and square matrixes.").build(),
                                    Project.builder()
                                            .title("ASP.NET Web Development Project")
                                            .description("This is a team project I made for the Web Development course, using the MVC-Architecture of ASP.NET framework, and the Entity Framework technology. For the authentication system, I used the Identity component. The general strategy I used for the implementation is Code-First.").build()))
                            .build())
                    .build();

            userService.saveElement(candidateUser);

            String jwtToken = jwtService.generateToken(candidateUser);
            String refreshToken = jwtService.generateRefreshToken(candidateUser);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (Exception x) {
            x.printStackTrace();
            return null;
        }
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
