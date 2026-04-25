package com.salman.AuthSystem.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.salman.AuthSystem.dtos.UserDto;
import com.salman.AuthSystem.interfaces.AuthService;
import com.salman.AuthSystem.interfaces.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserService userService; 
    private final PasswordEncoder passwordEncoder; 
    @Override
    public UserDto registerUser(UserDto userDto) {
      log.info("UserDTO: " + userDto.getEmail());
      userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
      UserDto userDto2 = userService.registerUser(userDto); 


      return userDto2; 
    }
    
}
