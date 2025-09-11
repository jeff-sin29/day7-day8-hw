package org.example.springbootdemo.repository.dao;

import org.example.springbootdemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeJpaRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByGender(String gender);
}
