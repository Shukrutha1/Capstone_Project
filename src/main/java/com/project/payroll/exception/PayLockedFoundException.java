package com.project.payroll.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PayLockedFoundException extends RuntimeException {

	public PayLockedFoundException(String message) {
		super(message);
	}
	

}
