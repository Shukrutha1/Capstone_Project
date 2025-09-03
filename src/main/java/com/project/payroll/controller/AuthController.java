package com.project.payroll.controller;

import com.project.payroll.dto.LoginRequest;
import com.project.payroll.dto.LoginResponse;
import com.project.payroll.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Authenticates a user and returns JWT token with user details")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
    	System.out.println("login called");
        LoginResponse response = authService.authenticate(loginRequest);
        return ResponseEntity.ok(response);
    }
}
