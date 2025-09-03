package com.project.payroll.service;

import com.project.payroll.dto.LeaveDTO;
import com.project.payroll.entity.Leave;
import java.util.List;

public interface LeaveService {
    LeaveDTO createLeave(LeaveDTO leave);
    List<LeaveDTO> getAllLeaves();
    LeaveDTO approveLeave(Long id);
    LeaveDTO rejectLeave(Long id);
    List<LeaveDTO> getLeavesByEmployeeId(Long employeeId);
}
