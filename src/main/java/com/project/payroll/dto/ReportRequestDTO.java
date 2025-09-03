package com.project.payroll.dto;

import lombok.Data;

@Data
public class ReportRequestDTO {
	
	private Long departId;
	private int year;
	private int month;

}
