package com.project.payroll.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private JobRole jobRole;

    private Double leaveBalance;

    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String phone;
    private String address;
    private String designation;
    private Double salary;

    // Getters and Setters
}
