package com.project.payroll.controller;

import com.project.payroll.dto.LeaveDTO;
import com.project.payroll.entity.Leave;
import com.project.payroll.service.LeaveService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/leaves")
@SecurityRequirement(name = "bearerAuth")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<LeaveDTO> createLeave(@RequestBody LeaveDTO leaveDTO) {
        LeaveDTO responseDTO = leaveService.createLeave(leaveDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LeaveDTO>> getAllLeaves() {
        List<LeaveDTO> leaves = leaveService.getAllLeaves();
        return ResponseEntity.ok(leaves);
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<LeaveDTO>> getLeavesByEmployee(@PathVariable Long employeeId) {
        List<LeaveDTO> leaves = leaveService.getLeavesByEmployeeId(employeeId);
        return ResponseEntity.ok(leaves);
    }

    @PutMapping("/{id}/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LeaveDTO> updateLeaveStatus(@PathVariable Long id, @PathVariable String status) {
        LeaveDTO leave;
        if ("approve".equalsIgnoreCase(status)) {
            leave = leaveService.approveLeave(id);
        } else if ("reject".equalsIgnoreCase(status)) {
            leave = leaveService.rejectLeave(id);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(leave);
    }
}
