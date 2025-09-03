package com.project.payroll.service;

import com.project.payroll.dto.JobRoleDTO;
import java.util.List;

public interface JobRoleService {
    List<JobRoleDTO> getAllJobs();
    JobRoleDTO createJob(JobRoleDTO jobDTO);
    JobRoleDTO updateJob(Long id, JobRoleDTO jobDTO);
    void deleteJob(Long id);
    JobRoleDTO getJobById(Long id);
}
