package com.salman.AuthSystem.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.salman.AuthSystem.dtos.UserDto;
import com.salman.AuthSystem.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDTO(User user); 
    User userDTOtoUser(UserDto userDto); 
    List<UserDto> userListToUserDTOList(List<User> userDtoList); 
    
    
}
