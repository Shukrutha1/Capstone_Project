package com.project.payroll.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.project.payroll.dto.PayRollDTO;
import com.project.payroll.dto.ReportRequestDTO;
import com.project.payroll.entity.Employee;
import com.project.payroll.entity.Payroll;
import com.project.payroll.repository.EmployeeRepository;
import com.project.payroll.repository.PayrollRepository;
import com.project.payroll.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	PayrollRepository payrollRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public List<PayRollDTO> getPayRollSummary(Integer year, Integer month) {
		
		List<Payroll> payRolls = payrollRepository.findDynamicPayrolls(Collections.EMPTY_LIST,year,
				month == 0 ? null: month);
		return payRolls.stream().map(PayRollDTO::new).toList();
		
	}

	@Override
	public List<PayRollDTO> getPayRollSummaryForDepartment(Long deptId, Integer year, Integer month) {
		  List<Long> empIds =  employeeRepository.findAllByDepartmentDepartmentId(deptId) 
				  .stream().map(Employee::getEmployeeId).toList(); 
		 
		List<Payroll> payRolls = payrollRepository.findDynamicPayrolls(CollectionUtils.isEmpty(empIds)
				? Collections.EMPTY_LIST : empIds,year,
				month == 0 ? null: month);
		return payRolls.stream().map(PayRollDTO::new).toList();
	}

}
