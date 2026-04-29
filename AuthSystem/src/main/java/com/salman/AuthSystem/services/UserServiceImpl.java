package com.salman.AuthSystem.services;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.salman.AuthSystem.models.Role;
import com.salman.AuthSystem.repositories.RoleRepository;
import com.salman.AuthSystem.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.salman.AuthSystem.dtos.ApiResponseDTO;
import com.salman.AuthSystem.dtos.UserDto;
import com.salman.AuthSystem.exceptions.ResourceNotFoundException;
import com.salman.AuthSystem.interfaces.UserService;
import com.salman.AuthSystem.mappers.UserMapper;
import com.salman.AuthSystem.models.Provider;
import com.salman.AuthSystem.models.User;
import com.salman.AuthSystem.repositories.UserRepository;
import com.salman.AuthSystem.utils.UserUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;


    @Override
    @Transactional
    public UserDto registerUser(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            log.error("Email is Required");
            throw new IllegalArgumentException("Email is Required!");
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            log.error("User Already exists");
            throw new IllegalArgumentException("User Already Exists");
        }

        User user = userMapper.userDTOtoUser(userDto);

        if (user.getRoles() == null) user.setRoles(new HashSet<>());

        user.setProvider(userDto.getProvider() != null ? userDto.getProvider() : Provider.LOCAL);

        Role role = roleRepository.findByName("ROLE_" + AppConstants.ROLE_USER).orElseThrow(() -> new RuntimeException("Default Role Not Found!"));


        user.getRoles().add(role);

        User savedUser = userRepository.saveAndFlush(user);

        if (savedUser.getUserId() != null) {
            log.info("User Created Successfully");
        }

        return userMapper.userToUserDTO(savedUser);


    }

    @Override
    public UserDto getUserById(String userId) {
        UUID uuid = UserUtils.parseUUID(userId);
        if (userId.isBlank()) {
            log.error("userId is Required");
            throw new IllegalArgumentException("User Id is Required");
        }

        User user = userRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("User with UserId: " + userId + "Not Found!"));

        return userMapper.userToUserDTO(user);

    }

    @Override
    @Transactional
    public ApiResponseDTO deleteUser(String userId) {
        UUID uuid = UserUtils.parseUUID(userId);
        if (userId.isBlank()) {
            log.error("userId is Required");
            throw new IllegalArgumentException("User Id is Required");
        }

        User user = userRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("User with UserId: " + userId + "Not Found!"));

        userRepository.delete(user);

        return new ApiResponseDTO(true, "Deleted the user with UserId: " + userId + " Successfully!", HttpStatus.OK, 200);

    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto) {
        UUID uuid = UserUtils.parseUUID(userId);
        if (userId.isBlank()) {
            log.error("userId is Required");
            throw new IllegalArgumentException("User Id is Required");
        }

        User existingUser = userRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("User with UserId: " + userId + "Not Found!"));

        if (userDto.getName() != null) existingUser.setName(userDto.getName());
        if (userDto.getImageUrl() != null) existingUser.setImageUrl(userDto.getImageUrl());
        if (userDto.getProvider() != null) existingUser.setProvider(userDto.getProvider());

        User updateUser = userRepository.save(existingUser);

        return userMapper.userToUserDTO(updateUser);

    }

    @Override
    @Transactional
    public List<UserDto> getAllUsers() {
        log.info("Fetching All Users from database....");
        List<User> users = userRepository.findAll();

        if (users.size() == 0 || users.isEmpty() || users == null) {
            log.info("No User Create. Please Create an User");

        }

        return userMapper.userListToUserDTOList(users);


    }

    @Override
    public UserDto getUserByEmail(String email) {
        log.info("Fetching user with Email: " + email + ".......");

        if (email == null || email.isBlank() || email.isEmpty()) {
            log.error("Email is Required!");
            throw new IllegalArgumentException("Email is Required!");
        }

        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User with Email: " + email + "Not Found!"));

        return userMapper.userToUserDTO(user);


    }

}
