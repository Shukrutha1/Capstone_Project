package com.project.payroll.repository;

import com.project.payroll.entity.JobRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRoleRepository extends JpaRepository<JobRole, Long> {
}
