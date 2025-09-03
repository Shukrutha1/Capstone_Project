package com.project.payroll.service.impl;

import com.project.payroll.config.JwtUtil;
import com.project.payroll.dto.LoginRequest;
import com.project.payroll.dto.LoginResponse;
import com.project.payroll.dto.UserDTO;
import com.project.payroll.entity.User;
import com.project.payroll.exception.AuthenticationFailedException;
import com.project.payroll.exception.UserNotFoundException;
import com.project.payroll.repository.UserRepository;
import com.project.payroll.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {

    	// Get user details after successful authentication
        User user = userRepository.findByUsername(loginRequest.getUsername())
            .orElseThrow(() -> new UserNotFoundException("User not found with username: " + loginRequest.getUsername() + " . PleaseRegister"));
        
            // Authenticate user credentials
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

        // Generate JWT token
        String token = jwtUtil.generateToken(buildUserDetails(user));

        // Build response with token and user details
        return LoginResponse.builder()
            .accessToken(token)
            .user(convertToUserDTO(user))
            .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
            
            return buildUserDetails(user);
        } catch (Exception e) {
            throw new AuthenticationFailedException("Failed to load user: " + e.getMessage());
        }
    }

    private UserDetails buildUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            Collections.singleton(new SimpleGrantedAuthority(user.getRole()))
        );
    }

    private UserDTO convertToUserDTO(User user) {
        return UserDTO.builder()
            .id(user.getUserId())
            .username(user.getUsername())
            .email(user.getEmail())
            .role(user.getRole())
            .build();
    }
}
