package com.project.payroll.service.impl;

import com.project.payroll.dto.EmployeeDTO;
import com.project.payroll.entity.Employee;
import com.project.payroll.entity.Department;
import com.project.payroll.entity.JobRole;
import com.project.payroll.entity.User;
import com.project.payroll.exception.*;
import com.project.payroll.repository.EmployeeRepository;
import com.project.payroll.repository.DepartmentRepository;
import com.project.payroll.repository.JobRoleRepository;
import com.project.payroll.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private JobRoleRepository jobRoleRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeDTO employeeDTO;
    private User user;
    private Department department;
    private JobRole jobRole;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setDob(LocalDate.now());
        employee.setPhone("1234567890");
        employee.setAddress("Address");
        employee.setDesignation("Developer");
        employee.setSalary(50000.0);

        user = new User();
        user.setUserId(2L);
        user.setUsername("admin");
        user.setRole("ROLE_ADMIN");
        employee.setUser(user);

        department = new Department();
        department.setDepartmentId(3L);
        department.setDepartmentName("IT");
        employee.setDepartment(department);

        jobRole = new JobRole();
        jobRole.setJobId(4L);
        jobRole.setJobTitle("Engineer");
        employee.setJobRole(jobRole);

        employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setDob(LocalDate.now());
        employeeDTO.setPhone("1234567890");
        employeeDTO.setAddress("Address");
        employeeDTO.setDesignation("Developer");
        employeeDTO.setSalary(50000.0);
        employeeDTO.setUserId(2L);
        employeeDTO.setDepartmentId(3L);
        employeeDTO.setJobId(4L);
    }

    @Test
    void testGetAllEmployees() {
        when(employeeRepository.findByDynamicFilters()).thenReturn(List.of(employee));
        List<EmployeeDTO> result = employeeService.getAllEmployees("IT", "Engineer", "John Doe");
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
    }

    @Test
    void testCreateEmployee() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));
        when(jobRoleRepository.findById(anyLong())).thenReturn(Optional.of(jobRole));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        EmployeeDTO result = employeeService.createEmployee(employeeDTO);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void testGetEmployeeById_Found() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        EmployeeDTO result = employeeService.getEmployeeById(1L);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(1L));
    }

    @Test
    void testUpdateEmployee_Found() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));
        when(jobRoleRepository.findById(anyLong())).thenReturn(Optional.of(jobRole));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        EmployeeDTO result = employeeService.updateEmployee(1L, employeeDTO);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void testUpdateEmployee_NotFound() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.updateEmployee(1L, employeeDTO));
    }

    @Test
    void testIsEmployeeOwnerOrAdmin_Admin() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        assertTrue(employeeService.isEmployeeOwnerOrAdmin(1L, "admin"));
    }

    @Test
    void testIsEmployeeOwnerOrAdmin_NotFound() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertFalse(employeeService.isEmployeeOwnerOrAdmin(1L, "admin"));
    }

	/*
	 * @Test void testIsEmployeeOwnerOrAdmin_Unauthorized() { User nonAdminUser =
	 * new User(); nonAdminUser.setUserId(5L); nonAdminUser.setUsername("user");
	 * nonAdminUser.setRole("ROLE_USER"); employee.setUser(nonAdminUser);
	 * when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee)
	 * ); when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(
	 * nonAdminUser)); assertThrows(UnauthorizedAccessException.class, () ->
	 * employeeService.isEmployeeOwnerOrAdmin(1L, "user")); }
	 */

    @Test
    void testDeleteEmployee_Found() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(any(Employee.class));
        assertDoesNotThrow(() -> employeeService.deleteEmployee(1L));
        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    void testDeleteEmployee_NotFound() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployee(1L));
    }
}
