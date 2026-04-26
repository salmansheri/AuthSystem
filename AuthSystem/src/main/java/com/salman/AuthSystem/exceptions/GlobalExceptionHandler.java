package com.salman.AuthSystem.exceptions;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.salman.AuthSystem.dtos.ApiResponseDTO;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        log.error("Validation failed: {}", errorMessage);

        ApiResponseDTO apiResponseDTO = new ApiResponseDTO(
                false,
                "Validation Failed -> " + errorMessage,
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value()
        );

        return ResponseEntity.badRequest().body(apiResponseDTO);


    }

    @ExceptionHandler({
            UsernameNotFoundException.class,
            BadCredentialsException.class,
            CredentialsExpiredException.class,
            DisabledException.class
//            ExpiredJwtException.class,
//            JwtException.class,
//            AuthenticationException.class
    })
    public ResponseEntity<ApiResponseDTO> handleAuthExceptions(Exception ex, HttpServletRequest request) {
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO(
                false,
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                400
        );

        return new ResponseEntity<ApiResponseDTO>(apiResponseDTO, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDTO> handleResourceNotFoundException(ResourceNotFoundException exception) {

        log.error("Error: " + exception.getMessage());
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO(true, exception.getMessage(), HttpStatus.NOT_FOUND, 404); 

        return new ResponseEntity<ApiResponseDTO>(apiResponseDTO, HttpStatus.NOT_FOUND); 

    }

     @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDTO> handleIllegalArgumentException(IllegalArgumentException exception) {

        log.error("Error: " + exception.getMessage());
        
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO(true, exception.getMessage(), HttpStatus.BAD_REQUEST, 400); 

        return new ResponseEntity<ApiResponseDTO>(apiResponseDTO, HttpStatus.BAD_REQUEST); 

    }
    
}
