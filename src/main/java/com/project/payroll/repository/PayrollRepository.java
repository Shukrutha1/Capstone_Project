package com.project.payroll.repository;

import com.project.payroll.entity.Payroll;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {

	@Query(value = "SELECT * FROM payroll " + "WHERE employee_id = :employeeId "
			+ "AND EXTRACT(YEAR FROM pay_date) = :year "
			+ "AND EXTRACT(MONTH FROM pay_date) = :month", nativeQuery = true)
	List<Payroll> findByEmployeeIdAndYearAndMonth(@Param("employeeId") Long employeeId, @Param("year") Integer year,
			@Param("month") Integer month);
	
	@Query(value = """
		    SELECT * FROM payroll 
		    WHERE (:employeeIds IS NULL OR employee_id IN (:employeeIds))
		      AND (:year IS NULL OR EXTRACT(YEAR FROM pay_date) = :year)
		      AND (:month IS NULL OR EXTRACT(MONTH FROM pay_date) = :month)
		    """, nativeQuery = true)
		List<Payroll> findDynamicPayrolls(
		        @Param("employeeIds") List<Long> employeeIds,
		        @Param("year") Integer year,
		        @Param("month") Integer month
		);

}
