package com.project.payroll.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "payroll")
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payrollId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private Double basicSalary;
    private Double deductions;
    private Double bonus;
    private Double netSalary;
    private Date payDate;
    
    @Column(name = "is_locked", nullable = false)
    private boolean locked = false;

    // Getters and Setters
}
