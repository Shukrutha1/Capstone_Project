package com.project.payroll.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class DepartmentDTO {
    private Long id;

    @NotBlank(message = "Department name is required")
    @Size(min = 2, max = 100, message = "Department name must be between 2 and 100 characters")
    private String departmentName;
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
    
    private boolean active;
    
    @Size(max = 50, message = "Cost center code cannot exceed 50 characters")
    private String costCenter;
}
