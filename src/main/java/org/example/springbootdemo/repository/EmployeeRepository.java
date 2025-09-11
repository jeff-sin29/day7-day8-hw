package org.example.springbootdemo.repository;

import lombok.Getter;
import lombok.Setter;
import org.example.springbootdemo.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


public interface EmployeeRepository {
    void addEmployee(Employee employee);

    Employee getEmployeeById(long id);

    List<Employee> getEmployeesByGender(String gender);

    Employee updateEmployeeById(long id, Employee updatedEmployee);

    boolean deleteEmployee(Employee employee);

    List<Employee> getEmployees();

    void clear();
}
