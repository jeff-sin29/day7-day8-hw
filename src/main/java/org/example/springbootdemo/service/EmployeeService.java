package org.example.springbootdemo.service;

import lombok.Getter;
import lombok.Setter;
import org.example.springbootdemo.entity.Employee;
import org.example.springbootdemo.exception.EmployeeAgeSalaryException;
import org.example.springbootdemo.exception.EmployeeNotInAgeRangeException;
import org.example.springbootdemo.exception.EmployeeNotFoundException;
import org.example.springbootdemo.exception.InactiveEmployeeUpdateException;
import org.example.springbootdemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    private int idCounter = 0;

    @Transactional
    public Map<String, Object> createEmployee(Employee employee){
        employee.setStatus(true);
        if (employee.getAge() < 18 || employee.getAge() > 65){
            throw new EmployeeNotInAgeRangeException();
        }
        if (employee.getAge() > 30 && employee.getSalary() < 20000){
            throw new EmployeeAgeSalaryException();
        }

        employeeRepository.addEmployee(employee);

        return Map.of("id", employee.getId());
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
        else if (!employeeRepository.getEmployeeById(id).getStatus()){
            throw new InactiveEmployeeUpdateException();
        }
        return employeeRepository.updateEmployeeById(id, updatedEmployee);
    }

    public void deleteEmployee(long id) {
        Employee employee = employeeRepository.getEmployeeById(id);
        employee.setStatus(false);
        boolean isRemoveSuccess = employeeRepository.deleteEmployee(employee);
        if (!isRemoveSuccess){
            throw new EmployeeNotFoundException("Employee with id " + id + " not found");
        }
    }
    @Transactional
    public void clearEmployeesList(){
        employeeRepository.clear();
        idCounter = 0;
    }
}
