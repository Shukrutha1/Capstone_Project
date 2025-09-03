package com.project.payroll.repository;

import com.project.payroll.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
    java.util.List<Leave> findByEmployeeEmployeeId(Long employeeId);
}
