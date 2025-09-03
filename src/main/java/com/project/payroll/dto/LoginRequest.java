package com.project.payroll.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "LoginRequest", description = "Login request payload")
public class LoginRequest {
    @Schema(example = "john.doe")
    private String username;
    @Schema(example = "secret123")
    private String password;
    // Getters and Setters
}
