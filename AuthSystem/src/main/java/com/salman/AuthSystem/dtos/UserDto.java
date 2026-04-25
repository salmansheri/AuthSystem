package com.salman.AuthSystem.dtos;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.salman.AuthSystem.RoleDto;
import com.salman.AuthSystem.models.Provider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    
    private UUID userId; 

     
    private String email; 

  
    private String name; 

 
    private String password; 

  
   
    private String imageUrl; 

    
    private boolean enabled = true;
    
   
    private Set<RoleDto> roles = new HashSet<>(); 

  
    private Provider provider = Provider.LOCAL; 
    
  
    private LocalDateTime createdAt; 
   
    private LocalDateTime updatedAt; 
    
}
