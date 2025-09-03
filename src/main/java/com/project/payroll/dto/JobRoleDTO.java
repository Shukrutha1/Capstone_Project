package com.project.payroll.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class JobRoleDTO {
    private Long id;

    private String jobTitle;
    
    private String description;
    
    private Double baseSalary;
    
    private boolean active;
}
