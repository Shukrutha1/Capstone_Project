package com.project.payroll.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.math.BigDecimal;

@Entity
@Table(name = "job_roles")
@Data
public class JobRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @Column(nullable = false, unique = true)
    private String jobTitle;
    
    private String description;
    
    @Column(nullable = false)
    private Double baseSalary;
    
    @OneToMany(mappedBy = "jobRole")
    private List<Employee> employees;
    
    @Column(name = "is_active")
    private boolean active = true;
}
