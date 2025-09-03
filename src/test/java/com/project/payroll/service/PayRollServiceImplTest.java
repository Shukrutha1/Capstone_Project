package com.project.payroll.service;

import com.project.payroll.dto.PayRollDTO;
import com.project.payroll.entity.Employee;
import com.project.payroll.entity.Payroll;
import com.project.payroll.entity.JobRole;
import com.project.payroll.exception.EmployeeNotFoundException;
import com.project.payroll.exception.PayLockedFoundException;
import com.project.payroll.exception.PayRollNotFoundException;
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
public class PayRollServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private PayrollRepository payrollRepository;
    @InjectMocks
    private PayRollServiceImpl payRollService;

    private Employee employee;
    private Payroll payroll;
    private JobRole jobRole;

    @BeforeEach
    void setUp() {
        jobRole = new JobRole();
        jobRole.setBaseSalary(10000.0);
        jobRole.setJobId(1L);
        jobRole.setJobTitle("Engineer");
        jobRole.setActive(true);

        employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setJobRole(jobRole);

        payroll = new Payroll();
        payroll.setEmployee(employee);
        payroll.setBasicSalary(10000.0);
        payroll.setBonus(1000.0);
        payroll.setDeductions(500.0);
        payroll.setNetSalary(10500.0);
        payroll.setPayDate(new Date());
        payroll.setLocked(false);
    }

    @Test
    void testCreatePayRollRun() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        when(payrollRepository.saveAll(anyList())).thenReturn(List.of(payroll));
        List<PayRollDTO> result = payRollService.createPayRollRun(2025, 9);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testProcessPayRollRun_FoundUnlocked() {
        when(payrollRepository.findById(anyLong())).thenReturn(Optional.of(payroll));
        when(payrollRepository.save(any(Payroll.class))).thenReturn(payroll);
        PayRollDTO result = payRollService.processPayRollRun(1L);
        assertNotNull(result);
    }

    @Test
    void testProcessPayRollRun_Locked() {
        payroll.setLocked(true);
        when(payrollRepository.findById(anyLong())).thenReturn(Optional.of(payroll));
        assertThrows(PayLockedFoundException.class, () -> payRollService.processPayRollRun(1L));
    }

    @Test
    void testProcessPayRollRun_NotFound() {
        when(payrollRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(PayRollNotFoundException.class, () -> payRollService.processPayRollRun(1L));
    }

    @Test
    void testProcessPayRollLock_Found() {
        when(payrollRepository.findById(anyLong())).thenReturn(Optional.of(payroll));
        when(payrollRepository.save(any(Payroll.class))).thenReturn(payroll);
        assertDoesNotThrow(() -> payRollService.processPayRollLock(1L));
        assertTrue(payroll.isLocked());
    }

    @Test
    void testProcessPayRollLock_NotFound() {
        when(payrollRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(PayRollNotFoundException.class, () -> payRollService.processPayRollLock(1L));
    }

    @Test
    void testGetPayRollById_Found() {
        when(payrollRepository.findById(anyLong())).thenReturn(Optional.of(payroll));
        PayRollDTO result = payRollService.getPayRollById(1L);
        assertNotNull(result);
    }

    @Test
    void testGetPayRollById_NotFound() {
        when(payrollRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(PayRollNotFoundException.class, () -> payRollService.getPayRollById(1L));
    }

    @Test
    void testGetPayRollByYearAndMonth_Found() {
        when(employeeRepository.findByUserUsername(anyString())).thenReturn(Optional.of(employee));
        when(payrollRepository.findByEmployeeIdAndYearAndMonth(anyLong(), anyInt(), anyInt())).thenReturn(List.of(payroll));
        List<PayRollDTO> result = payRollService.getPayRollByYearAndMonth(2025, 9, "user");
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetPayRollByYearAndMonth_EmployeeNotFound() {
        when(employeeRepository.findByUserUsername(anyString())).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> payRollService.getPayRollByYearAndMonth(2025, 9, "user"));
    }
}
