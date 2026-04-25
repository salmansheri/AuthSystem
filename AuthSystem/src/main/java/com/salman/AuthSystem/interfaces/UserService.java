package com.salman.AuthSystem.interfaces;

import java.util.List;

import com.salman.AuthSystem.dtos.ApiResponseDTO;
import com.salman.AuthSystem.dtos.UserDto;

public interface UserService {
    UserDto registerUser(UserDto userRequestDto);
    UserDto getUserById(String userId);
    ApiResponseDTO deleteUser(String userId); 
    UserDto updateUser(String userId, UserDto userDto); 
    List<UserDto> getAllUsers(); 
    UserDto getUserByEmail(String email); 


}
