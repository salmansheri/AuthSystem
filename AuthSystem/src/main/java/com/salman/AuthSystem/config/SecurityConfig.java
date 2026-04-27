package com.salman.AuthSystem.config;

import com.salman.AuthSystem.dtos.ApiResponseDTO;
import com.salman.AuthSystem.filters.JwtAuthFilter;
import com.salman.AuthSystem.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;


    @Bean
    public SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests ->
                                requests.requestMatchers("/api/v1/auth/**")
                                        .permitAll()
                                        .requestMatchers("/swagger-ui/**").permitAll()
                                        .requestMatchers("/v3/api-docs/**").permitAll()
                                        .requestMatchers("/swagger-ui.html").permitAll()
                                        .requestMatchers("/error").permitAll()

//                    .requestMatchers("/api/v1/users/**").permitAll()


                                        .anyRequest()
                                        .authenticated()
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint((request, response, aex) -> {
                    aex.printStackTrace();
                    response.setStatus(401);
                    response.setContentType("application/json");
                    String message = "Unauthorized Access: " + aex.getMessage();
                    String error = (String) request.getAttribute("error");
                    if (error != null) {
                        message = error;
                    }
                    ApiResponseDTO apiResponseDTO = new ApiResponseDTO(false, message, HttpStatus.UNAUTHORIZED, 401);

                    var objectMapper = new ObjectMapper();
                    response.getWriter().write(objectMapper.writeValueAsString(apiResponseDTO));


                }));


        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());
        http.sessionManagement(smc -> smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();

    }


}
