package com.project.payroll.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.project.payroll.dto.PayRollDTO;
import com.project.payroll.service.PayRollService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/payroll")
@Tag(name = "Pay roll Management", description = "APIs for managing pay rolls")
@SecurityRequirement(name = "bearerAuth")
public class PayrollController {
	
	@Autowired
	PayRollService payRollService;
	
	@PostMapping("/{year}/{month}")
	@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new payroll run", description = "Creates a new payroll run for given year and month")
    public ResponseEntity<List<PayRollDTO>> createPayrollRun(@PathVariable int year, @PathVariable int month) {
		
        return ResponseEntity.ok(payRollService.createPayRollRun(year, month));
    }

    @PostMapping("/runs/{id}/process")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new payroll run", description = "Creates a new payroll run for specified payroll")
    public ResponseEntity<PayRollDTO> processPayrollRun(@PathVariable Long id) {
    	return ResponseEntity.ok(payRollService.processPayRollRun(id));
    }

    @PostMapping("/runs/{id}/lock")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Locks specific payroll from run", description = "Locks specified payroll for further run")
    public ResponseEntity<Void> lockPayrollRun(@PathVariable Long id) {
        payRollService.processPayRollLock(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/runs/{id}/items")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Fetches the payroll", description = "Gets the  payroll details  for specified Id")
    public ResponseEntity<PayRollDTO> getPayrollItems(@PathVariable Long id) {
        return ResponseEntity.ok(payRollService.getPayRollById(id));
    }

    @GetMapping("/my/{year}/{month}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    @Operation(summary = "Create a new payroll run", description = "Creates a new payroll run for specified payroll")
    public ResponseEntity<List<PayRollDTO>> getMyNetPay(Principal principal, @PathVariable int year, @PathVariable int month) {
    	
        return ResponseEntity.ok(payRollService.getPayRollByYearAndMonth(year,month,principal.getName()));
    }
}
