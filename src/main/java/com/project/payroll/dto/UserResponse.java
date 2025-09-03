package com.project.payroll.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserResponse", description = "User details in login response")
public class UserResponse {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "john.doe")
    private String username;
    @Schema(example = "EMPLOYEE")
    private String role;
    // Getters and Setters
}
