package org.example.springbootdemo.repository.dao;

import org.example.springbootdemo.entity.Company;
import org.example.springbootdemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyJpaRepository extends JpaRepository<Company, Long> {

}
