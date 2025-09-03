package com.project.payroll.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
    private boolean active;
    // Exclude password for security
}
