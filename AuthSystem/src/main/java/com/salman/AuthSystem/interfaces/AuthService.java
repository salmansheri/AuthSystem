package com.salman.AuthSystem.interfaces;

import com.salman.AuthSystem.dtos.UserDto;

public interface AuthService {
    UserDto registerUser(UserDto userDto); 
    // UserDto signIn(); 
}
