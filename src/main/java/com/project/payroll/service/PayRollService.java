package com.project.payroll.service;

import java.util.List;

import com.project.payroll.dto.PayRollDTO;

public interface PayRollService {

	List<PayRollDTO> createPayRollRun(int year, int month);

	PayRollDTO processPayRollRun(Long id);

	void processPayRollLock(Long id);

	PayRollDTO getPayRollById(Long id);

	List<PayRollDTO> getPayRollByYearAndMonth(int year, int month, String name);

}
