package com.project.payroll.exception;

import java.time.LocalDateTime;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    static class ApiError {
        public final LocalDateTime timestamp = LocalDateTime.now();
        public final int status;
        public final String error;
        public final String message;
        public final String path;

        public ApiError(HttpStatus status, String message, String path) {
            this.status = status.value();
            this.error = status.getReasonPhrase();
            this.message = message;
            this.path = path;
        }
    }

    @ExceptionHandler(PayRollNotFoundException.class)
    public ResponseEntity<ApiError> handlePayrollNotFound(PayRollNotFoundException ex, HttpServletRequest req) {
        return new ResponseEntity<>(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PayLockedFoundException.class)
    public ResponseEntity<ApiError> handlePayrollLocked(PayLockedFoundException ex, HttpServletRequest req) {
        return new ResponseEntity<>(new ApiError(HttpStatus.CONFLICT, ex.getMessage(), req.getRequestURI()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ApiError> handleUnauthorized(UnauthorizedAccessException ex, HttpServletRequest req) {
        return new ResponseEntity<>(new ApiError(HttpStatus.FORBIDDEN, ex.getMessage(), req.getRequestURI()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ApiError> handleAuthFailed(AuthenticationFailedException ex, HttpServletRequest req) {
        return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage(), req.getRequestURI()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex, HttpServletRequest req) {
        return new ResponseEntity<>(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JobRoleNotFoundException.class)
    public ResponseEntity<ApiError> handleJobRoleNotFound(JobRoleNotFoundException ex, HttpServletRequest req) {
        return new ResponseEntity<>(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiError> handleEmployeeNotFound(EmployeeNotFoundException ex, HttpServletRequest req) {
        return new ResponseEntity<>(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ApiError> handleDepartmentNotFound(DepartmentNotFoundException ex, HttpServletRequest req) {
        return new ResponseEntity<>(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String msg = ex.getBindingResult().getAllErrors().stream().findFirst().map(e -> e.getDefaultMessage()).orElse(ex.getMessage());
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, msg, req.getRequestURI()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAll(Exception ex, HttpServletRequest req) {
        return new ResponseEntity<>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), req.getRequestURI()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}