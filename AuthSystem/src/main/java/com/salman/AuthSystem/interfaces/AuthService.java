package com.salman.AuthSystem.interfaces;

import com.salman.AuthSystem.dtos.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    UserDto registerUser(UserDto userDto);

    SignInResponseDTO signIn(SignInRequestDTO requestDTO, HttpServletResponse response);

    // UserDto signIn();
    SignInResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO, HttpServletRequest request, HttpServletResponse response);
    ApiResponseDTO signOut(HttpServletRequest request, HttpServletResponse response);

}
