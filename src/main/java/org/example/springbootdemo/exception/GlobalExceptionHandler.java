package org.example.springbootdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleEmployeeNotFoundException(EmployeeNotFoundException ex) {}

    @ExceptionHandler(EmployeeNotInAgeRangeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleEmployeeNotInAgeRangeException(EmployeeNotInAgeRangeException ex) {}

    @ExceptionHandler(EmployeeAgeSalaryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleEmployeeAgeSalaryException(EmployeeAgeSalaryException ex) {}

    @ExceptionHandler(InactiveEmployeeUpdateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleInactiveEmployeeUpdateException(InactiveEmployeeUpdateException ex) {}
}
