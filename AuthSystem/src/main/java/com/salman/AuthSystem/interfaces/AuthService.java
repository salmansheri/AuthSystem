package com.salman.AuthSystem.interfaces;

import com.salman.AuthSystem.dtos.RefreshTokenRequestDTO;
import com.salman.AuthSystem.dtos.SignInRequestDTO;
import com.salman.AuthSystem.dtos.SignInResponseDTO;
import com.salman.AuthSystem.dtos.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    UserDto registerUser(UserDto userDto);

    SignInResponseDTO signIn(SignInRequestDTO requestDTO, HttpServletResponse response);

    // UserDto signIn();
    SignInResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO, HttpServletRequest request, HttpServletResponse response);
}
