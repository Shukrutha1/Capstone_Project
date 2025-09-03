package com.project.payroll.dto;

import java.util.Date;

import com.project.payroll.entity.Payroll;

import lombok.Data;

@Data
public class PayRollDTO {
	private Long payrollId;
    private Double basicSalary;
    private Double deductions;
    private Double bonus;
    private Double netSalary;
    private Date payDate;
    private boolean locked;
    
    public PayRollDTO (Payroll payroll) {
    	this.payrollId = payroll.getPayrollId();
        this.basicSalary = payroll.getBasicSalary();
        this.deductions = payroll.getDeductions();
        this.bonus = payroll.getBonus();
        this.netSalary = payroll.getNetSalary();
        this.payDate = payroll.getPayDate();
        this.locked = payroll.isLocked();
    }

}
