package com.project.payroll.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "leave_request")
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private Date startDate;
    private Date endDate;
    private String leaveType; // e.g., Sick, Casual, Paid
    private String status; // Pending / Approved / Rejected
    private String reason;

    // Getters and Setters
}
