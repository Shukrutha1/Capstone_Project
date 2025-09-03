package com.project.payroll.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.payroll.dto.PayRollDTO;
import com.project.payroll.entity.Employee;
import com.project.payroll.entity.Payroll;
import com.project.payroll.exception.EmployeeNotFoundException;
import com.project.payroll.exception.PayLockedFoundException;
import com.project.payroll.exception.PayRollNotFoundException;
import com.project.payroll.repository.EmployeeRepository;
import com.project.payroll.repository.PayrollRepository;
import com.project.payroll.service.PayRollService;


@Service
public class PayRollServiceImpl implements PayRollService {
	
	private final Double bonus = 10.0;
	private final Double deduction = 5.0;
	
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	PayrollRepository payrollRepository;
	

	@Override
	public List<PayRollDTO> createPayRollRun(int year, int month) {
		Date date = createDateWithYearMonth(year, month);
		List<Payroll> payRolls = new ArrayList<>();
		List<Employee> employees = employeeRepository.findAll();
		if(!CollectionUtils.isEmpty(employees)) {
			employees.stream().forEach(e -> {
				Payroll payroll = new Payroll();
				Double baseSalary = e.getJobRole().getBaseSalary();
				payroll.setBasicSalary(baseSalary);
				payroll.setBonus(calcBonus(baseSalary));
				payroll.setDeductions(calcDeduction(baseSalary + payroll.getBonus()));
				payroll.setNetSalary((baseSalary+payroll.getBonus()) - payroll.getDeductions());
				payroll.setPayDate(date);
				payroll.setLocked(false);
				payroll.setEmployee(e);
				payRolls.add(payroll);
			});
		}
		
		payrollRepository.saveAll(payRolls);
		return convertoDto(payRolls);
	}
	
	@Override
	public PayRollDTO processPayRollRun(Long id) {
		Payroll payroll = payrollRepository.findById(id)
				.orElseThrow(() -> new PayRollNotFoundException("pay roll not found for given id: "+ id));
		Double baseSalary = payroll.getEmployee().getJobRole().getBaseSalary();
		if(payroll.isLocked()) { throw new PayLockedFoundException("The payroll with id "+id+" is locked for further run.");}
		payroll.setBonus(calcBonus(baseSalary));
		payroll.setDeductions(calcDeduction(baseSalary + payroll.getBonus()));
		payroll.setNetSalary((baseSalary+payroll.getBonus()) - payroll.getDeductions());
		payroll.setPayDate(new Date());
		
		payrollRepository.save(payroll);
		
		return new PayRollDTO(payroll);
	}
	
	@Override
	public void processPayRollLock(Long id) {
		Payroll payroll = payrollRepository.findById(id)
				.orElseThrow(() -> new PayRollNotFoundException("pay roll not found for given id: "+ id));
		payroll.setLocked(true);
		payrollRepository.save(payroll);
	}
	
	@Override
	public PayRollDTO getPayRollById(Long id) {
		Payroll payroll = payrollRepository.findById(id)
				.orElseThrow(() -> new PayRollNotFoundException("pay roll not found for given id: "+ id));
		return new PayRollDTO(payroll);
	}
	
	@Override
	public List<PayRollDTO> getPayRollByYearAndMonth(int year, int month, String name) {
		Optional<Employee> employeeOpt = employeeRepository.findByUserUsername(name);
		if(employeeOpt.isEmpty()) { throw new EmployeeNotFoundException("Employee not found for given name : "+name);}
		return payrollRepository.findByEmployeeIdAndYearAndMonth(employeeOpt.get().getEmployeeId(), year, month)
		.stream().map(PayRollDTO::new).toList();
	}

	private List<PayRollDTO> convertoDto(List<Payroll> payRolls) {
		// TODO Auto-generated method stub
		return payRolls.stream().map(PayRollDTO::new).toList();
	}

	private Double calcDeduction(double d) {
		return d * (deduction/100.0);
	}

	private Double calcBonus(Double baseSalary) {	
		return baseSalary * (bonus/100.0);
	}

	private Date createDateWithYearMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();

        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        // Month in Calendar is zero-based (0 = January, 11 = December)
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1); // adjust for 0-based months
        calendar.set(Calendar.DAY_OF_MONTH, currentDay);

        return calendar.getTime();
	}

}
