package com.project.payroll.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PayRollNotFoundException extends RuntimeException {

	public PayRollNotFoundException(String message) {
		super(message);
	}
	

}
