package com.project.payroll.repository;

import com.project.payroll.entity.Employee;
import com.project.payroll.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "SELECT e FROM Employee e")
    List<Employee> findByDynamicFilters();

	Optional<Employee> findByUserUsername(String username);

	List<Employee> findAllByDepartmentDepartmentId(Long departId);
}
