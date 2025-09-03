package com.project.payroll.service.impl;

import com.project.payroll.dto.PayRollDTO;
import com.project.payroll.entity.Employee;
import com.project.payroll.entity.Payroll;
import com.project.payroll.repository.EmployeeRepository;
import com.project.payroll.repository.PayrollRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {
    @Mock
    private PayrollRepository payrollRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private ReportServiceImpl reportService;

    private Payroll payroll;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setEmployeeId(1L);
        payroll = new Payroll();
        payroll.setEmployee(employee);
    }

    @Test
    void testGetPayRollSummary() {
        when(payrollRepository.findDynamicPayrolls(anyList(), anyInt(), any())).thenReturn(List.of(payroll));
        List<PayRollDTO> result = reportService.getPayRollSummary(2025, 9);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testGetPayRollSummaryForDepartment_WithEmployees() {
        when(employeeRepository.findAllByDepartmentDepartmentId(anyLong())).thenReturn(List.of(employee));
        when(payrollRepository.findDynamicPayrolls(anyList(), anyInt(), any())).thenReturn(List.of(payroll));
        List<PayRollDTO> result = reportService.getPayRollSummaryForDepartment(1L, 2025, 9);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testGetPayRollSummaryForDepartment_NoEmployees() {
        when(employeeRepository.findAllByDepartmentDepartmentId(anyLong())).thenReturn(Collections.emptyList());
        when(payrollRepository.findDynamicPayrolls(anyList(), anyInt(), any())).thenReturn(Collections.emptyList());
        List<PayRollDTO> result = reportService.getPayRollSummaryForDepartment(1L, 2025, 9);
        assertTrue(result.isEmpty());
    }
}
