package com.project.payroll.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

import com.project.payroll.entity.Leave;

@Data
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String phone;
    private String address;
    private String designation;
    private Long departmentId;
    private Long jobId;
    private Double salary;
    private Long userId; // Reference to the user account
    private List<LeaveDTO> leaves;
}
