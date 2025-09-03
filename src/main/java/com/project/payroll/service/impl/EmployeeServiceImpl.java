package com.project.payroll.service.impl;

import com.project.payroll.dto.EmployeeDTO;
import com.project.payroll.dto.LeaveDTO;
import com.project.payroll.entity.Employee;
import com.project.payroll.entity.Department;
import com.project.payroll.entity.JobRole;
import com.project.payroll.entity.Leave;
import com.project.payroll.entity.User;
import com.project.payroll.repository.EmployeeRepository;
import com.project.payroll.repository.DepartmentRepository;
import com.project.payroll.repository.JobRoleRepository;
import com.project.payroll.repository.LeaveRepository;
import com.project.payroll.repository.UserRepository;
import com.project.payroll.service.EmployeeService;
import com.project.payroll.service.LeaveService;
//import com.project.payroll.service.SalaryStructureDTO;
import com.project.payroll.exception.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private JobRoleRepository jobRoleRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private LeaveService leaveService;

    @Override
    public List<EmployeeDTO> getAllEmployees(String department, String job, String name) {
        StringBuilder queryBuilder = new StringBuilder();
        List<String> conditions = new ArrayList<>();

        if (department != null && !department.trim().isEmpty()) {
            conditions.add("e.department.departmentName LIKE '%" + department + "%'");
        }
        
        if (job != null && !job.trim().isEmpty()) {
            conditions.add("e.jobRole.jobTitle LIKE '%" + job + "%'");
        }
        
        if (name != null && !name.trim().isEmpty()) {
            conditions.add("LOWER(CONCAT(e.firstName, ' ', e.lastName)) LIKE '%" + name.toLowerCase() + "%'");
        }

        if (!conditions.isEmpty()) {
            queryBuilder.append("WHERE ").append(String.join(" AND ", conditions));
        }

        return employeeRepository.findByDynamicFilters()
                .stream().map(this::convertToDTO).toList();
    }

    @Override
    @Transactional
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        updateEmployeeFromDTO(employee, employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDTO(savedEmployee);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
        		.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        return convertToDTO(employee);
    }

    @Override
    @Transactional
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        updateEmployeeFromDTO(employee, employeeDTO);
        Employee updatedEmployee = employeeRepository.save(employee);
        return convertToDTO(updatedEmployee);
    }

    @Override
	public EmployeeDTO getCurrentEmployee(String name) {
		// TODO Auto-generated method stub
		Employee employee = employeeRepository.findByUserUsername(name)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with username: " + name));
		List<LeaveDTO> leaves = leaveService.getLeavesByEmployeeId(employee.getEmployeeId());
		
		EmployeeDTO employeeDTO = convertToDTO(employee);
		employeeDTO.setLeaves(leaves);
		return employeeDTO;
		
	}
    
    @Override
    public boolean isEmployeeOwnerOrAdmin(Long employeeId, String username) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isEmpty()) {
            return false;
        }
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        if (!user.getRole().equals("ROLE_ADMIN") && 
            (employee.get().getUser() == null || 
             !employee.get().getUser().getUsername().equals(username))) {
            throw new UnauthorizedAccessException("User does not have permission to access this employee record");
        }
        return true;
    }
    
    @Override
	public void deleteEmployee(Long id) {
    	Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    	employeeRepository.delete(employee);
		
	}

    private void updateEmployeeFromDTO(Employee employee, EmployeeDTO dto) {
    	if (dto.getFirstName() != null) {
    	    employee.setFirstName(dto.getFirstName());
    	}
    	if (dto.getLastName() != null) {
    	    employee.setLastName(dto.getLastName());
    	}
    	if (dto.getDob() != null) {
    	    employee.setDob(dto.getDob());
    	}
    	if (dto.getPhone() != null) {
    	    employee.setPhone(dto.getPhone());
    	}
    	if (dto.getAddress() != null) {
    	    employee.setAddress(dto.getAddress());
    	}
    	if (dto.getDesignation() != null) {
    	    employee.setDesignation(dto.getDesignation());
    	}
    	if (dto.getSalary() != null) {
    	    employee.setSalary(dto.getSalary());
    	}
        
        if (dto.getUserId() != null) {
        User user = userRepository.findById(dto.getUserId())
        		.orElseThrow(() -> new UserNotFoundException("User not found with id: " + dto.getUserId()));
        employee.setUser(user);
        }
        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: " + dto.getDepartmentId()));
            employee.setDepartment(department);
        }
        
        if (dto.getJobId() != null) {
            JobRole jobRole = jobRoleRepository.findById(dto.getJobId())
                    .orElseThrow(() -> new JobRoleNotFoundException("Job role not found with id: " + dto.getJobId()));
            employee.setJobRole(jobRole);
        }
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getEmployeeId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setDob(employee.getDob());
        dto.setPhone(employee.getPhone());
        dto.setAddress(employee.getAddress());
        dto.setDesignation(employee.getDesignation());
        dto.setSalary(employee.getSalary());
        
        if (employee.getDepartment() != null) {
            dto.setDepartmentId(employee.getDepartment().getDepartmentId());
        }
        if (employee.getJobRole() != null) {
            dto.setJobId(employee.getJobRole().getJobId());
        }
        if (employee.getUser() != null) {
            dto.setUserId(employee.getUser().getUserId());
        }
        return dto;
    }
}
