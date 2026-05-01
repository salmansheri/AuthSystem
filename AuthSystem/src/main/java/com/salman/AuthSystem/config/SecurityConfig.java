package com.salman.AuthSystem.config;

import com.salman.AuthSystem.dtos.ApiResponseDTO;
import com.salman.AuthSystem.filters.JwtAuthFilter;
import com.salman.AuthSystem.models.User;
import com.salman.AuthSystem.utils.AppConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
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
                                        .requestMatchers("/api/v1/users/**").hasRole(AppConstants.ROLE_ADMIN)


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


                }).accessDeniedHandler(((request, response, accessDeniedException) -> {
                    response.setStatus(403);
                    response.setContentType("/application/json");
                    String message = accessDeniedException.getMessage();
                    String error = (String) request.getAttribute("error");

                    if (error != null) {
                        message = error;

                    }

                    ApiResponseDTO apiResponseDTO = new ApiResponseDTO(
                            false,
                            message,
                            HttpStatus.FORBIDDEN,
                            HttpStatus.FORBIDDEN.value()
                    );

                    ObjectMapper objectMapper = new ObjectMapper();

                    response.getWriter().write(objectMapper.writeValueAsString(apiResponseDTO));




                        }))
                );


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


    @Bean
    public CorsConfigurationSource corsConfigurationSource(@Value("${frontend.url}")  String frontendUrl) {
        String[] urls = frontendUrl.trim().split(",");

        var config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(urls));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;

    }


}
