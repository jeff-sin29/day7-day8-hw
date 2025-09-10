package org.example.springbootdemo.service;

import org.example.springbootdemo.entity.Employee;
import org.example.springbootdemo.exception.EmployeeAgeSalaryException;
import org.example.springbootdemo.exception.EmployeeNotInAgeRangeException;
import org.example.springbootdemo.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {
    @InjectMocks
    EmployeeService employeeService;

    @Mock
    EmployeeRepository employeeRepository;

    @Test
    void should_throw_exception_when_create_employee_given_age_below_18(){
        assertThrows(EmployeeNotInAgeRangeException.class, () -> {
            employeeService.createEmployee(new Employee(1, "Mary", 17, 5000, "Female"));
        });
        verify(employeeRepository, never()).addEmployee(any());
    }

    @Test
    void should_throw_exception_when_create_employee_given_age_above_65(){
        assertThrows(EmployeeNotInAgeRangeException.class, () -> {
            employeeService.createEmployee(new Employee(1, "Ken", 66, 7000, "Male"));
        });
        verify(employeeRepository, never()).addEmployee(any());
    }

}