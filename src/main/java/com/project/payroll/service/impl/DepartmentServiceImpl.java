package com.project.payroll.service.impl;

import com.project.payroll.dto.DepartmentDTO;
import com.project.payroll.entity.Department;
import com.project.payroll.exception.DepartmentNotFoundException;
import com.project.payroll.repository.DepartmentRepository;
import com.project.payroll.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = convertToEntity(departmentDTO);
        department = departmentRepository.save(department);
        return convertToDTO(department);
    }

    @Override
    @Transactional
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: " + id));

        department.setDepartmentName(departmentDTO.getDepartmentName());
        department.setDescription(departmentDTO.getDescription());
        department.setActive(departmentDTO.isActive());
        department.setCostCenter(departmentDTO.getCostCenter());

        department = departmentRepository.save(department);
        return convertToDTO(department);
    }

    @Override
    @Transactional
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: " + id));
        departmentRepository.delete(department);
    }

    @Override
    public DepartmentDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: " + id));
        return convertToDTO(department);
    }

    private DepartmentDTO convertToDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getDepartmentId());
        dto.setDepartmentName(department.getDepartmentName());
        dto.setDescription(department.getDescription());
        dto.setActive(department.isActive());
        dto.setCostCenter(department.getCostCenter());
        return dto;
    }

    private Department convertToEntity(DepartmentDTO dto) {
        Department department = new Department();
        department.setDepartmentName(dto.getDepartmentName());
        department.setDescription(dto.getDescription());
        department.setActive(dto.isActive());
        department.setCostCenter(dto.getCostCenter());
        return department;
    }
}
