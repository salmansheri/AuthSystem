package com.salman.AuthSystem.dtos;

import java.util.HashSet;
import java.util.Set;

import com.salman.AuthSystem.RoleDto;
import com.salman.AuthSystem.models.Provider;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserRequestDto {
  
       @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email; 

     @NotBlank(message = "Name is required")
    @Size(min = 3, max=50, message = "Name must be between 3 and 50 characters")
    private String name; 

   @NotBlank(message = "password is required")
    @Size(min=6, message = "Password must be at least 6 characters")
    private String password; 

  
    @Pattern(
        regexp = "^(http|https)://.*$",
        message = "Image URL must be valid"
    )
    private String imageUrl; 

    
    private boolean enabled = true;
    
   
    private Set<RoleDto> roles = new HashSet<>(); 

  
    private Provider provider = Provider.LOCAL; 
    
  
  
}
