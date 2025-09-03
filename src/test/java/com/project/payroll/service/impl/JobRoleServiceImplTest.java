package com.project.payroll.service.impl;

import com.project.payroll.dto.JobRoleDTO;
import com.project.payroll.entity.JobRole;
import com.project.payroll.exception.JobRoleNotFoundException;
import com.project.payroll.repository.JobRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JobRoleServiceImplTest {
    @Mock
    private JobRoleRepository jobRoleRepository;

    @InjectMocks
    private JobRoleServiceImpl jobRoleService;

    private JobRole jobRole;
    private JobRoleDTO jobRoleDTO;

    @BeforeEach
    void setUp() {
        jobRole = new JobRole();
        jobRole.setJobId(1L);
        jobRole.setJobTitle("Engineer");
        jobRole.setDescription("Engineering role");
        jobRole.setBaseSalary(60000.0);
        jobRole.setActive(true);

        jobRoleDTO = new JobRoleDTO();
        jobRoleDTO.setId(1L);
        jobRoleDTO.setJobTitle("Engineer");
        jobRoleDTO.setDescription("Engineering role");
        jobRoleDTO.setBaseSalary(60000.0);
        jobRoleDTO.setActive(true);
    }

    @Test
    void testGetAllJobs() {
        when(jobRoleRepository.findAll()).thenReturn(List.of(jobRole));
        List<JobRoleDTO> result = jobRoleService.getAllJobs();
        assertEquals(1, result.size());
        assertEquals("Engineer", result.get(0).getJobTitle());
    }

    @Test
    void testCreateJob() {
        when(jobRoleRepository.save(any(JobRole.class))).thenReturn(jobRole);
        JobRoleDTO result = jobRoleService.createJob(jobRoleDTO);
        assertEquals("Engineer", result.getJobTitle());
    }

    @Test
    void testUpdateJob_Found() {
        when(jobRoleRepository.findById(anyLong())).thenReturn(Optional.of(jobRole));
        when(jobRoleRepository.save(any(JobRole.class))).thenReturn(jobRole);
        JobRoleDTO result = jobRoleService.updateJob(1L, jobRoleDTO);
        assertEquals("Engineer", result.getJobTitle());
    }

    @Test
    void testUpdateJob_NotFound() {
        when(jobRoleRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(JobRoleNotFoundException.class, () -> jobRoleService.updateJob(1L, jobRoleDTO));
    }

    @Test
    void testDeleteJob_Found() {
        when(jobRoleRepository.findById(anyLong())).thenReturn(Optional.of(jobRole));
        doNothing().when(jobRoleRepository).delete(any(JobRole.class));
        assertDoesNotThrow(() -> jobRoleService.deleteJob(1L));
        verify(jobRoleRepository, times(1)).delete(jobRole);
    }

    @Test
    void testDeleteJob_NotFound() {
        when(jobRoleRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(JobRoleNotFoundException.class, () -> jobRoleService.deleteJob(1L));
    }

    @Test
    void testGetJobById_Found() {
        when(jobRoleRepository.findById(anyLong())).thenReturn(Optional.of(jobRole));
        JobRoleDTO result = jobRoleService.getJobById(1L);
        assertEquals("Engineer", result.getJobTitle());
    }

    @Test
    void testGetJobById_NotFound() {
        when(jobRoleRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(JobRoleNotFoundException.class, () -> jobRoleService.getJobById(1L));
    }
}
