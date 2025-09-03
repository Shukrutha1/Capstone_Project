package com.project.payroll.service.impl;

import com.project.payroll.dto.LeaveDTO;
import com.project.payroll.entity.Employee;
import com.project.payroll.entity.Leave;
import com.project.payroll.repository.EmployeeRepository;
import com.project.payroll.repository.LeaveRepository;
import com.project.payroll.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {
    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public LeaveDTO createLeave(LeaveDTO dto) {
        Leave leave = convertToEntity(dto);
        leave.setStatus("Pending");
        return convertToDTO(leaveRepository.save(leave));
    }

    @Override
    public List<LeaveDTO> getAllLeaves() {
        List<Leave> leaves = leaveRepository.findAll();
        return leaves.stream().map(this::convertToDTO).toList();
    }

    @Override
    public LeaveDTO approveLeave(Long id) {
        Leave leave = leaveRepository.findById(id).orElseThrow();
        leave.setStatus("Approved");
        return convertToDTO(leaveRepository.save(leave));
    }

    @Override
    public LeaveDTO rejectLeave(Long id) {
        Leave leave = leaveRepository.findById(id).orElseThrow();
        leave.setStatus("Rejected");
        return convertToDTO(leaveRepository.save(leave));
    }

    @Override
    public List<LeaveDTO> getLeavesByEmployeeId(Long employeeId) {
        return leaveRepository.findByEmployeeEmployeeId(employeeId).stream()
                .map(this::convertToDTO)
                .toList();
    }

    // Conversion methods
    private Leave convertToEntity(LeaveDTO dto) {
        Leave leave = new Leave();
        leave.setLeaveId(dto.getLeaveId());
        if (dto.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(dto.getEmployeeId()).orElse(null);
            leave.setEmployee(employee);
        }
        leave.setStartDate(dto.getStartDate());
        leave.setEndDate(dto.getEndDate());
        leave.setLeaveType(dto.getLeaveType());
        leave.setStatus(dto.getStatus());
        leave.setReason(dto.getReason());
        return leave;
    }

    private LeaveDTO convertToDTO(Leave leave) {
        LeaveDTO dto = new LeaveDTO();
        dto.setLeaveId(leave.getLeaveId());
        dto.setEmployeeId(leave.getEmployee() != null ? leave.getEmployee().getEmployeeId() : null);
        dto.setStartDate(leave.getStartDate());
        dto.setEndDate(leave.getEndDate());
        dto.setLeaveType(leave.getLeaveType());
        dto.setStatus(leave.getStatus());
        dto.setReason(leave.getReason());
        return dto;
    }

    // Overloaded createLeave for DTO
    // public LeaveDTO createLeave(LeaveDTO leaveDTO) {
    //     Leave leave = convertToEntity(leaveDTO);
    //     leave.setStatus("Pending");
    //     Leave saved = leaveRepository.save(leave);
    //     return convertToDTO(saved);
    // }
}
