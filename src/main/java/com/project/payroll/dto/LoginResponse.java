package com.project.payroll.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "LoginResponse", description = "Login response payload")
public class LoginResponse {
    @Schema(example = "eyJhbGciOiJIUzI1...")
    private String accessToken;
    private UserDTO user;
    // Getters and Setters
}
