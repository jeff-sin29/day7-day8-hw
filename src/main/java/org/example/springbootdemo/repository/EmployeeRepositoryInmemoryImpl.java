package org.example.springbootdemo.repository;

import org.example.springbootdemo.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryInmemoryImpl implements EmployeeRepository {
    public List<Employee> employees = new ArrayList<>();

    @Override
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    @Override
    public Employee getEmployeeById(long id) {
        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public List<Employee> getEmployeesByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender() != null && employee.getGender().equalsIgnoreCase(gender))
                .toList();
    }

    @Override
    public Employee updateEmployeeById(long id, Employee updatedEmployee) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                employee.setAge(updatedEmployee.getAge());
                employee.setSalary(updatedEmployee.getSalary());
                return employee ;
            }
        }
        return null;
    }

    @Override
    public boolean deleteEmployee(Employee employee) {
        return employees.stream()
                .filter(e -> e.getId() == employee.getId())
                .findFirst()
                .map(e -> {
                    e.setStatus(false);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public List<Employee> getEmployees() {
        return employees;
    }

    @Override
    public void clear() {
        employees.clear();
    }
}
