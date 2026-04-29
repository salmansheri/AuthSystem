package com.salman.AuthSystem.controllers;

import com.salman.AuthSystem.dtos.ApiResponseDTO;
import com.salman.AuthSystem.dtos.UserDto;
import com.salman.AuthSystem.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
   
    private final UserService userService;

   

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String userId, @RequestBody UserDto userDto) {
        
        
        return new ResponseEntity<>(userService.updateUser(userId, userDto), HttpStatus.OK);
    }

      @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseDTO> deleteUser
    (@PathVariable String userId) {
        
        
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }
    
    
}
