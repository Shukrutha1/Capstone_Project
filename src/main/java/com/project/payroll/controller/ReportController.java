package com.project.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.project.payroll.dto.PayRollDTO;
import com.project.payroll.dto.ReportRequestDTO;
import com.project.payroll.service.ReportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/reports")
@Tag(name = "Report Management", description = "APIs for Reports")
@SecurityRequirement(name = "bearerAuth")
public class ReportController {
	
	@Autowired
	ReportService reportService;
	
    @GetMapping("/payroll-summary")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Gets payroll summary for given period", description = "Gets payroll summary for given period")
    public ResponseEntity<List<PayRollDTO>> getPayrollSummary( @RequestParam Integer year, @RequestParam(required = false, defaultValue="0" ) Integer month) {
        return ResponseEntity.ok(reportService.getPayRollSummary(year, month));
    }

    @GetMapping("/department-cost")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Gets payroll summary for given department", description = "Gets payroll summary for given department")
    public ResponseEntity<List<PayRollDTO>> getDepartmentCost(@RequestParam Long departmentId, @RequestParam Integer year, @RequestParam(required = false, defaultValue = "0") Integer month) {
        return ResponseEntity.ok(reportService.getPayRollSummaryForDepartment(departmentId,year,month));
    }
}
