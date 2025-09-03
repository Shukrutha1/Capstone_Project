package com.project.payroll.service.impl;

import com.project.payroll.dto.JobRoleDTO;
import com.project.payroll.entity.JobRole;
import com.project.payroll.exception.JobRoleNotFoundException;
import com.project.payroll.repository.JobRoleRepository;
import com.project.payroll.service.JobRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobRoleServiceImpl implements JobRoleService {

    @Autowired
    private JobRoleRepository jobRoleRepository;

    @Override
    public List<JobRoleDTO> getAllJobs() {
        return jobRoleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public JobRoleDTO createJob(JobRoleDTO jobDTO) {
        JobRole jobRole = convertToEntity(jobDTO);
        jobRole = jobRoleRepository.save(jobRole);
        return convertToDTO(jobRole);
    }

    @Override
    @Transactional
    public JobRoleDTO updateJob(Long id, JobRoleDTO jobDTO) {
        JobRole jobRole = jobRoleRepository.findById(id)
                .orElseThrow(() -> new JobRoleNotFoundException("Job role not found with id: " + id));
        System.out.println("job found" +jobRole.getJobTitle());

        updateJobRoleFromDTO(jobRole, jobDTO);
        jobRole = jobRoleRepository.save(jobRole);
        return convertToDTO(jobRole);
    }

    @Override
    @Transactional
    public void deleteJob(Long id) {
        JobRole jobRole = jobRoleRepository.findById(id)
                .orElseThrow(() -> new JobRoleNotFoundException("Job role not found with id: " + id));
        jobRoleRepository.delete(jobRole);
    }

    @Override
    public JobRoleDTO getJobById(Long id) {
        JobRole jobRole = jobRoleRepository.findById(id)
                .orElseThrow(() -> new JobRoleNotFoundException("Job role not found with id: " + id));
        return convertToDTO(jobRole);
    }

    private JobRoleDTO convertToDTO(JobRole jobRole) {
        JobRoleDTO dto = new JobRoleDTO();
        dto.setId(jobRole.getJobId());
        dto.setJobTitle(jobRole.getJobTitle());
        dto.setDescription(jobRole.getDescription());
        dto.setBaseSalary(jobRole.getBaseSalary());
        dto.setActive(jobRole.isActive());
        return dto;
    }

    private JobRole convertToEntity(JobRoleDTO dto) {
        JobRole jobRole = new JobRole();
        updateJobRoleFromDTO(jobRole, dto);
        return jobRole;
    }

    private void updateJobRoleFromDTO(JobRole jobRole, JobRoleDTO dto) {
    	if(dto.getJobTitle() != null) { jobRole.setJobTitle(dto.getJobTitle());}
    	if(dto.getDescription() != null) { jobRole.setDescription(dto.getDescription()); }
    	if(dto.getBaseSalary() != null) { jobRole.setBaseSalary(dto.getBaseSalary()); }
    	 jobRole.setActive(dto.isActive());
    }
}
