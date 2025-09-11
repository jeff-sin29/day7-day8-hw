package org.example.springbootdemo.repository;

import org.example.springbootdemo.entity.Employee;
import org.example.springbootdemo.repository.dao.EmployeeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepositoryDBImpl implements EmployeeRepository {

    @Autowired
    private EmployeeJpaRepository employeeJpaRepository;

    @Override
    public void addEmployee(Employee employee) {
        employeeJpaRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(long id) {
        return employeeJpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> getEmployeesByGender(String gender) {
        return employeeJpaRepository.findByGender(gender);
    }

    @Override
    public Employee updateEmployeeById(long id, Employee updatedEmployee) {
        if (employeeJpaRepository.existsById(id)) {
            updatedEmployee.setId(id);
            return employeeJpaRepository.save(updatedEmployee);
        }
        return null;
    }

    @Override
    public boolean deleteEmployee(Employee employee) {
        if (employeeJpaRepository.existsById(employee.getId())) {
            employeeJpaRepository.delete(employee);
            return true;
        }
        return false;
    }

    @Override
    public List<Employee> getEmployees() {
        return employeeJpaRepository.findAll();
    }

    @Override
    public void clear() {
        employeeJpaRepository.deleteAll();
    }
}
