package org.example.springbootdemo.service;

import lombok.Getter;
import lombok.Setter;
import org.example.springbootdemo.entity.Employee;
import org.example.springbootdemo.exception.EmployeeAgeSalaryException;
import org.example.springbootdemo.exception.EmployeeNotInAgeRangeException;
import org.example.springbootdemo.exception.EmployeeNotFoundException;
import org.example.springbootdemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    private int idCounter = 0;

    public Map<String, Object> createEmployee(Employee employee){
        if (employee.getAge() < 18 || employee.getAge() > 65){
            throw new EmployeeNotInAgeRangeException();
        }
        if (employee.getAge() > 30 && employee.getSalary() < 20000){
            throw new EmployeeAgeSalaryException();
        }
        int newId = ++idCounter;
        employee.setId(newId);
        employeeRepository.addEmployee(employee);
        return Map.of("id", newId);
    }

    public List<Employee> getEmployees(){
        return employeeRepository.getEmployees();
    }

    public Employee getEmployeeById(long id){
        Employee employee = employeeRepository.getEmployeeById(id);
        if (employee == null){
            throw new EmployeeNotFoundException("Employee with id " + id + " not found");
        }
        return employee;
    }

    public List<Employee> getEmployeesByGender(String gender){
        return employeeRepository.getEmployeesByGender(gender);
    }

    public List<Employee> getEmployeesByPageAndSize(int page, int size) {
        List<Employee> employees = employeeRepository.getEmployees();
        int fromIndex = Math.max((page - 1) * size, 0);
        int toIndex = Math.min(fromIndex + size, employees.size());
        return employees.subList(fromIndex, toIndex);
    }

    public Employee updateEmployee(long id, Employee updatedEmployee) {
        if (employeeRepository.getEmployeeById(id) == null){
            throw new EmployeeNotFoundException("Employee with id " + id + " not found");
        }
        return employeeRepository.updateEmployeeById(id, updatedEmployee);
    }

    public void deleteEmployee(long id) {
        boolean isRemoveSuccess = employeeRepository.deleteEmployeeById(id);
        if (!isRemoveSuccess){
            throw new EmployeeNotFoundException("Employee with id " + id + " not found");
        }
    }

    public void clearEmployeesList(){
        employeeRepository.getEmployees().clear();
        idCounter = 0;
    }
}
