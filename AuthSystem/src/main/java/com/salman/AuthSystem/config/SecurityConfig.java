package com.salman.AuthSystem.config;

import com.salman.AuthSystem.dtos.ApiResponseDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> 
            requests.requestMatchers("/api/v1/auth/sign-up")
            .permitAll()
            .requestMatchers("/swagger-ui/**").permitAll()
            .requestMatchers("/v3/api-docs/**").permitAll()
            .requestMatchers("/swagger-ui.html").permitAll()
//                    .requestMatchers("/api/v1/users/**").permitAll()

           
            .anyRequest()
            .authenticated()
        )
                        .exceptionHandling(ex -> ex.authenticationEntryPoint((request, response, aex) -> {
                            aex.printStackTrace();
                            response.setStatus(401);
                            response.setContentType("application/json");
                            ApiResponseDTO apiResponseDTO = new ApiResponseDTO(false, aex.getMessage(), HttpStatus.UNAUTHORIZED, 401);

                            response.getWriter().write(apiResponseDTO.toString());

                        }));

      
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());
        http.sessionManagement(smc -> smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build(); 
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 

    }

   
    
}
