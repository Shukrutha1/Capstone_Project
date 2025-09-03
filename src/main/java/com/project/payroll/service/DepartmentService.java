package com.project.payroll.service;

import com.project.payroll.dto.DepartmentDTO;
import java.util.List;

public interface DepartmentService {
    List<DepartmentDTO> getAllDepartments();
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);
    DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO);
    void deleteDepartment(Long id);
    DepartmentDTO getDepartmentById(Long id);
}
