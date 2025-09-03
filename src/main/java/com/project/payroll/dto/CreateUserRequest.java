package com.project.payroll.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;

@Data
public class CreateUserRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @Email
    private String email;
    @NotNull
    private String role;  // ROLE_ADMIN or ROLE_EMPLOYEE
}
