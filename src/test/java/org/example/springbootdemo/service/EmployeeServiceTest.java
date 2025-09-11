package org.example.springbootdemo.service;

import org.example.springbootdemo.dto.UpdateEmployeeReq;
import org.example.springbootdemo.entity.Employee;
import org.example.springbootdemo.exception.EmployeeAgeSalaryException;
import org.example.springbootdemo.exception.EmployeeNotInAgeRangeException;
import org.example.springbootdemo.exception.InactiveEmployeeUpdateException;
import org.example.springbootdemo.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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

    @Captor
    private ArgumentCaptor<Employee> employeeArgumentCaptor;

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
            employeeService.createEmployee(new Employee("Ken", 66, 7000, "Male"));
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

    @Test
    void should_return_employee_with_active_status_when_create_employee_given_correct_employee() {
        Employee employee = new Employee(1, "Tom", 20, 2000, "Male");
        employeeService.createEmployee(employee);
        verify(employeeRepository,times(1)).addEmployee(employeeArgumentCaptor.capture());
        Employee value = employeeArgumentCaptor.getValue();
        assertTrue(value.getStatus());
    }

    @Test
    void should_set_status_false_when_delete_employee_given_existing_id() {
        Employee employee = new Employee(1, "John", 25, 5000, "Male");
        employee.setStatus(true);

        when(employeeRepository.deleteEmployee(employee)).thenReturn(true);
        when(employeeRepository.getEmployeeById(1)).thenReturn(employee);

        employee.setStatus(false);
        employeeService.deleteEmployee(1);

        assertFalse(employee.getStatus());
        verify(employeeRepository, times(1)).deleteEmployee(employee);
    }

    @Test
    void should_fail_to_update_employee_given_inactive_employee() {
        Employee employee = new Employee(1, "Jack", 25, 5000, "Male");

        employee.setStatus(false);
        when(employeeRepository.getEmployeeById(1)).thenReturn(employee);

        UpdateEmployeeReq updatedEmployee = new UpdateEmployeeReq("Jack", 26, 7000);

        assertThrows(InactiveEmployeeUpdateException.class, () -> {
            employeeService.updateEmployee(1L, updatedEmployee);
        });
    }

}