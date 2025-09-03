package com.project.payroll.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;


@Entity
@Table(name = "departments")
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    @Column(nullable = false, unique = true)
    private String departmentName;
    
    private String description;
    
    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
    
    @Column(name = "is_active")
    private boolean active = true;
    
    @Column(name = "cost_center")
    private String costCenter;
}
