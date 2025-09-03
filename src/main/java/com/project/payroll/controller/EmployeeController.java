package com.project.payroll.controller;

import com.project.payroll.dto.EmployeeDTO;
import com.project.payroll.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@SecurityRequirement(name = "bearerAuth")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "List employees with optional filters", description = "Admin access only")
    public ResponseEntity<List<EmployeeDTO>> listEmployees(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String job,
            @RequestParam(required = false) String name) {
        return ResponseEntity.ok(employeeService.getAllEmployees(department, job, name));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new employee record", description = "Admin access only")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.createEmployee(employeeDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee profile", description = "Admin access or Self access for employees")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id, Principal principal) {
    	return ResponseEntity.ok(employeeService.getEmployeeById(id));
                
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update employee details", description = "Admin access only")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDTO));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletes employee details of given Id.", description = "Admin access only")
    public ResponseEntity<Void> updateEmployee(
            @PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('EMPLOYEE')")
    @Operation(summary = "Get current employee details", description = "Retrieves the details of the currently logged-in employee")
    public ResponseEntity<EmployeeDTO> getCurrentUser(Principal principal) {
    	System.out.println("principal" + principal);
        return ResponseEntity.ok(employeeService.getCurrentEmployee(principal.getName()));
    }
}
