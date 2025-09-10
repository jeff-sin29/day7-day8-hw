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

    @Test
    void should_create_employee_given_employee_correct_condition() {
        Employee employee = new Employee(1, "Kenny", 27, 5000, "Male");

        Map<String, Object> idMap = employeeService.createEmployee(employee);
        assertEquals(idMap.get("id"), 1);
    }

    @Test
    void should_return_single_employee_given_exist_employee_id() {
        Employee employee = new Employee(1, "Alice", 30, 5000, "Female");

        when(employeeRepository.getEmployeeById(1)).thenReturn(employee);

        Employee foundEmployee = employeeService.getEmployeeById(1);

        assertEquals(foundEmployee.getId(), employee.getId());
        assertEquals(foundEmployee.getName(), employee.getName());
        assertEquals(foundEmployee.getAge(), employee.getAge());
        assertEquals(foundEmployee.getSalary(), employee.getSalary());
        assertEquals(foundEmployee.getGender(), employee.getGender());

        verify(employeeRepository, times(1)).getEmployeeById(1);
    }

    @Test
    void should_throw_exception_when_below_salary_range_and_above_age_range() {
        assertThrows(EmployeeAgeSalaryException.class, () -> {
            employeeService.createEmployee(new Employee(1, "Tom", 40, 15000, "Male"));
        });
    }

}