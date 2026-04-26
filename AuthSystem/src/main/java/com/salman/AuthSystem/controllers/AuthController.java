package com.salman.AuthSystem.controllers;

import com.salman.AuthSystem.dtos.SignInRequestDTO;
import com.salman.AuthSystem.dtos.SignInResponseDTO;
import com.salman.AuthSystem.dtos.UserDto;
import com.salman.AuthSystem.interfaces.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponseDTO> signIn(@RequestBody SignInRequestDTO requestDTO) {
        SignInResponseDTO signInResponseDTO = authService.signIn(requestDTO);

        return new ResponseEntity<SignInResponseDTO>(signInResponseDTO, HttpStatus.OK);
    }


    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUp(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<UserDto>(authService.registerUser(userDto), HttpStatus.CREATED); 

        

    }


    
}
