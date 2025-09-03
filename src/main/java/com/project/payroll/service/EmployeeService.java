package com.project.payroll.service;

import com.project.payroll.dto.EmployeeDTO;
import com.project.payroll.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees(String department, String job, String name);
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO getEmployeeById(Long id);
    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);
   // List<SalaryStructureDTO> getSalaryStructures(Long employeeId);
    boolean isEmployeeOwnerOrAdmin(Long employeeId, String username);
	void deleteEmployee(Long id);
	EmployeeDTO getCurrentEmployee(String name);
}
