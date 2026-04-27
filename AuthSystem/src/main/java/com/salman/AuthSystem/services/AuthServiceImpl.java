package com.salman.AuthSystem.services;

import com.salman.AuthSystem.dtos.SignInRequestDTO;
import com.salman.AuthSystem.dtos.SignInResponseDTO;
import com.salman.AuthSystem.interfaces.CookieService;
import com.salman.AuthSystem.mappers.UserMapper;
import com.salman.AuthSystem.models.RefreshToken;
import com.salman.AuthSystem.models.User;
import com.salman.AuthSystem.repositories.RefreshTokenRepository;
import com.salman.AuthSystem.repositories.UserRepository;
import com.salman.AuthSystem.utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.salman.AuthSystem.dtos.UserDto;
import com.salman.AuthSystem.interfaces.AuthService;
import com.salman.AuthSystem.interfaces.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CookieService cookieService;

    @Override
    public UserDto registerUser(UserDto userDto) {
        log.info("UserDTO: " + userDto.getEmail());
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserDto userDto2 = userService.registerUser(userDto);


        return userDto2;
    }

    @Override
    public SignInResponseDTO signIn(SignInRequestDTO requestDTO, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword()));
            User user = userRepository.findByEmail(requestDTO.getEmail()).orElseThrow(() -> new BadCredentialsException("Invalid Username!"));

            if (!user.isEnabled()) {
                throw new DisabledException("User is disabled");

            }

            String jti = UUID.randomUUID().toString();

            RefreshToken refreshTokenEntity = RefreshToken.builder()
                    .jti(jti)
                    .user(user)
                    .expiresAt(LocalDateTime.now().plusSeconds(jwtUtils.getRefreshTtlSeconds()))
                    .revoked(false)
                    .build();

            RefreshToken savedRefreshTokenEntity = refreshTokenRepository.save(refreshTokenEntity);

            String accessToken = jwtUtils.generateAccessToken(user);
            String refreshToken = jwtUtils.generateRefreshToken(user, savedRefreshTokenEntity.getJti());

            cookieService.attachRefreshTokenCookie(response, refreshToken, (int) jwtUtils.getAccessTtlSeconds());
            cookieService.addNoStoreHeader(response);


            return new SignInResponseDTO(
                    accessToken,
                    refreshToken,
                    jwtUtils.getAccessTtlSeconds(),
                    userMapper.userToUserDTO(user)


            );


        } catch (Exception ex) {
            log.error("Error: {}", ex.getMessage());
            throw new BadCredentialsException("Invalid Username or Password", ex);

        }

    }

}
