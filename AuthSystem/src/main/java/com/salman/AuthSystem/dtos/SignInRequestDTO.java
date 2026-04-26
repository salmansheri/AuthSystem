package com.salman.AuthSystem.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.processing.SupportedAnnotationTypes;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequestDTO {

    @NotBlank
    @Email(message = "Enter Valid Email Address!")
    private String email;

    @NotBlank
    @NotNull
    @Size(min = 4, max = 50, message = "Password Must be at least 4 letter and upto 50 letter")
    private String password;
}
