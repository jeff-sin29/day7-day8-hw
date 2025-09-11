package org.example.springbootdemo.repository;

import lombok.Getter;
import lombok.Setter;
import org.example.springbootdemo.entity.Company;
import org.example.springbootdemo.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface CompanyRepository {
    public Company getCompanyById(long id);

    public List<Company> getAllCompanies();

    public void addCompany(Company company);

    public Company updateCompanyById(long id, Company updatedCompany);

    public Company deleteCompanyById(long id);

    public void clear();


}
