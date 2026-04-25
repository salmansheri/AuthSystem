package com.salman.AuthSystem.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salman.AuthSystem.dtos.UserDto;
import com.salman.AuthSystem.interfaces.AuthService;
import com.salman.AuthSystem.interfaces.UserService;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService; 

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUp(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<UserDto>(authService.registerUser(userDto), HttpStatus.CREATED); 

        

    }


    
}
