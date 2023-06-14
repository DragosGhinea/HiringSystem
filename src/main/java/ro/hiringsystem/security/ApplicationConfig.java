package ro.hiringsystem.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.hiringsystem.model.dto.UserDto;
import ro.hiringsystem.service.UserService;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserService<UserDto> userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a UserDetailsService bean based on the userService.
     *
     * @return the UserDetailsService bean
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return userService::getByEmail;
    }

    /**
     * Creates an AuthenticationManager bean based on the AuthenticationConfiguration.
     *
     * @param config the AuthenticationConfiguration
     * @return the AuthenticationManager bean
     * @throws Exception if an error occurs while retrieving the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Creates an AuthenticationProvider bean based on the UserDetailsService and PasswordEncoder.
     *
     * @return the AuthenticationProvider bean
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
}
