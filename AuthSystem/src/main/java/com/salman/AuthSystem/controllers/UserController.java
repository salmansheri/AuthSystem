package com.salman.AuthSystem.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salman.AuthSystem.dtos.ApiResponseDTO;
import com.salman.AuthSystem.dtos.UserDto;
import com.salman.AuthSystem.interfaces.UserService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
   
    private final UserService userService;

   

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam String param) {
        return new ResponseEntity<List<UserDto>>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String email) {
        return new ResponseEntity<UserDto>(userService.getUserByEmail(email), HttpStatus.OK ); 
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String userId, @RequestBody UserDto userDto) {
        
        
        return new ResponseEntity<>(userService.updateUser(userId, userDto), HttpStatus.OK);
    }

      @PutMapping("/{userId}")
    public ResponseEntity<ApiResponseDTO> delteUser
    (@PathVariable String userId) {
        
        
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }
    
    
}
