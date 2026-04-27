package com.salman.AuthSystem.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter


public class SignInResponseDTO {
    private String accessToken;
    private String refreshToken;
    private long expiresIn;
    private String tokenType = "Bearer";
    private UserDto user;

    public SignInResponseDTO(String accessToken, String refreshToken, long expiresIn, UserDto user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.user = user;
    }
}
