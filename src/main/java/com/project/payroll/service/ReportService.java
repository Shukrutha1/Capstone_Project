package com.project.payroll.service;

import java.util.List;

import com.project.payroll.dto.PayRollDTO;
import com.project.payroll.dto.ReportRequestDTO;

public interface ReportService {

	List<PayRollDTO> getPayRollSummary(Integer year, Integer month);

	List<PayRollDTO> getPayRollSummaryForDepartment(Long deptId, Integer year, Integer month);

}
