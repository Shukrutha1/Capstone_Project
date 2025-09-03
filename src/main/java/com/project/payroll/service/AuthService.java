package com.project.payroll.service;

import com.project.payroll.dto.LoginRequest;
import com.project.payroll.dto.LoginResponse;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    LoginResponse authenticate(LoginRequest loginRequest);
    UserDetails loadUserByUsername(String username);
}
