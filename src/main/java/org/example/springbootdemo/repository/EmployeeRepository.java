package org.example.springbootdemo.repository;

import lombok.Getter;
import lombok.Setter;
import org.example.springbootdemo.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Repository
public class EmployeeRepository {
    public List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public Employee getEmployeeById(long id) {
        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst().orElse(null);
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender() != null && employee.getGender().equalsIgnoreCase(gender))
                .toList();
    }

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

    public boolean deleteEmployeeById(long id) {
        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .map(employee -> {
                    employee.setStatus(false);
                    return true;
                })
                .orElse(false);
    }

}
