package com.salman.AuthSystem.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.salman.AuthSystem.exceptions.ResourceNotFoundException;
import com.salman.AuthSystem.models.User;
import com.salman.AuthSystem.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository; 

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("Username: " + username + " Not Found!")); 

       return user; 

       
    }
    
}
