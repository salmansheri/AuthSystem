package com.salman.AuthSystem.dtos;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ApiResponseDTO {
    private Boolean success; 
    private String message; 
    private HttpStatus status; 
    private int statusCode; 
    private LocalDateTime timeStamp = LocalDateTime.now();

    public ApiResponseDTO(Boolean success, String message, HttpStatus status, int statusCode) {
        this.success = success; 
        this.message =message;
        this.statusCode = statusCode; 
        this.status = status;  
    }


    @Override
    public String toString() {
        return "ApiResponseDTO{" +
                "timeStamp=" + timeStamp +
                ", statusCode=" + statusCode +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", success=" + success +
                '}';
    }
}
