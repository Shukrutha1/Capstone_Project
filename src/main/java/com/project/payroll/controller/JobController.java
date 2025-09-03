package com.project.payroll.controller;

import com.project.payroll.dto.JobRoleDTO;
import com.project.payroll.service.JobRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@Tag(name = "Job Management", description = "APIs for managing job roles")
@SecurityRequirement(name = "bearerAuth")
public class JobController {

    @Autowired
    private JobRoleService jobRoleService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all job roles", description = "Retrieves a list of all job roles")
    public ResponseEntity<List<JobRoleDTO>> getAllJobs() {
        return ResponseEntity.ok(jobRoleService.getAllJobs());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new job role", description = "Creates a new job role")
    public ResponseEntity<JobRoleDTO> createJob(@Valid @RequestBody JobRoleDTO jobDTO) {
        return new ResponseEntity<>(jobRoleService.createJob(jobDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a job role", description = "Updates an existing job role's details")
    public ResponseEntity<JobRoleDTO> updateJob(
            @PathVariable Long id,
            @Valid @RequestBody JobRoleDTO jobDTO) {
        return ResponseEntity.ok(jobRoleService.updateJob(id, jobDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a job role", description = "Deletes an existing job role")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobRoleService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
}
