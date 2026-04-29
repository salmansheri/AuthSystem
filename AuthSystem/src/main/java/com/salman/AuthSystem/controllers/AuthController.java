package com.salman.AuthSystem.controllers;

import com.salman.AuthSystem.dtos.*;
import com.salman.AuthSystem.interfaces.AuthService;
import com.salman.AuthSystem.interfaces.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CookieService cookieService;

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponseDTO> signIn(@Valid @RequestBody SignInRequestDTO requestDTO, HttpServletResponse response) {
        SignInResponseDTO signInResponseDTO = authService.signIn(requestDTO, response);

        return new ResponseEntity<SignInResponseDTO>(signInResponseDTO, HttpStatus.OK);
    }


    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUp(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<UserDto>(authService.registerUser(userDto), HttpStatus.CREATED);


    }

    @PostMapping("/sign-out")
    public ResponseEntity<ApiResponseDTO> signOut(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<ApiResponseDTO>(authService.signOut(request, response), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<SignInResponseDTO> refreshToken(@RequestBody(required = false) RefreshTokenRequestDTO refreshTokenRequestDTO, HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<SignInResponseDTO>(authService.refreshToken(refreshTokenRequestDTO, request, response), HttpStatus.OK);


    }


}
