package org.example.springbootdemo.repository;

import org.example.springbootdemo.entity.Company;
import org.example.springbootdemo.repository.dao.CompanyJpaRepository;
import org.example.springbootdemo.repository.dao.EmployeeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyRepositoryDBImpl implements CompanyRepository{
    @Autowired
    private CompanyJpaRepository companyJpaRepository;

    public Company getCompanyById(long id) {
        return companyJpaRepository.findById(id).orElse(null);
    }

    public List<Company> getAllCompanies() {
        return companyJpaRepository.findAll();
    }

    public void addCompany(Company company) {
        companyJpaRepository.save(company);
    }

    public Company updateCompanyById(long id, Company updatedCompany) {
        if (companyJpaRepository.existsById(id)) {
            updatedCompany.setId(id);
            return companyJpaRepository.save(updatedCompany);
        }
        return null;
    }

    public Company deleteCompanyById(long id) {
        if (companyJpaRepository.existsById(id)) {
            Company company = companyJpaRepository.findById(id).orElse(null);
            companyJpaRepository.deleteById(id);
            return company;
        }
        return null;
    }

    public void clear() {
        companyJpaRepository.deleteAll();
    }

}
