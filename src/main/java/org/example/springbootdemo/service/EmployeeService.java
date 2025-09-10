package org.example.springbootdemo.service;

import lombok.Getter;
import lombok.Setter;
import org.example.springbootdemo.entity.Employee;
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
        int newId = ++idCounter;
        employee.setId(newId);
        employeeRepository.addEmployee(employee);
        return Map.of("id", newId);
    }

    public List<Employee> getEmployees(){
        return employeeRepository.getEmployees();
    }

    public Employee getEmployeeById(long id){
        return employeeRepository.getEmployeeById(id);
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
        return employeeRepository.updateEmployeeById(id, updatedEmployee);
    }

    public boolean deleteEmployee(long id) {
        return employeeRepository.deleteEmployeeById(id);
    }

    public void clearEmployeesList(){
        employeeRepository.getEmployees().clear();
        idCounter = 0;
    }
}
