package com.salman.AuthSystem.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.salman.AuthSystem.dtos.ApiResponseDTO;

import lombok.extern.slf4j.Slf4j;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

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
