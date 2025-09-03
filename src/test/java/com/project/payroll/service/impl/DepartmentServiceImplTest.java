package com.project.payroll.service.impl;

import com.project.payroll.dto.DepartmentDTO;
import com.project.payroll.entity.Department;
import com.project.payroll.exception.DepartmentNotFoundException;
import com.project.payroll.repository.DepartmentRepository;
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
public class DepartmentServiceImplTest {
    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private Department department;
    private DepartmentDTO departmentDTO;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setDepartmentId(1L);
        department.setDepartmentName("IT");
        department.setDescription("Information Technology");
        department.setActive(true);
        department.setCostCenter("CC001");

        departmentDTO = new DepartmentDTO();
        departmentDTO.setId(1L);
        departmentDTO.setDepartmentName("IT");
        departmentDTO.setDescription("Information Technology");
        departmentDTO.setActive(true);
        departmentDTO.setCostCenter("CC001");
    }

    @Test
    void testGetAllDepartments() {
        when(departmentRepository.findAll()).thenReturn(List.of(department));
        List<DepartmentDTO> result = departmentService.getAllDepartments();
        assertEquals(1, result.size());
        assertEquals("IT", result.get(0).getDepartmentName());
    }

    @Test
    void testCreateDepartment() {
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        DepartmentDTO result = departmentService.createDepartment(departmentDTO);
        assertEquals("IT", result.getDepartmentName());
    }

    @Test
    void testUpdateDepartment_Found() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        DepartmentDTO result = departmentService.updateDepartment(1L, departmentDTO);
        assertEquals("IT", result.getDepartmentName());
    }

    @Test
    void testUpdateDepartment_NotFound() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(DepartmentNotFoundException.class, () -> departmentService.updateDepartment(1L, departmentDTO));
    }

    @Test
    void testDeleteDepartment_Found() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));
        doNothing().when(departmentRepository).delete(any(Department.class));
        assertDoesNotThrow(() -> departmentService.deleteDepartment(1L));
        verify(departmentRepository, times(1)).delete(department);
    }

    @Test
    void testDeleteDepartment_NotFound() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(DepartmentNotFoundException.class, () -> departmentService.deleteDepartment(1L));
    }

    @Test
    void testGetDepartmentById_Found() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));
        DepartmentDTO result = departmentService.getDepartmentById(1L);
        assertEquals("IT", result.getDepartmentName());
    }

    @Test
    void testGetDepartmentById_NotFound() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(DepartmentNotFoundException.class, () -> departmentService.getDepartmentById(1L));
    }
}
