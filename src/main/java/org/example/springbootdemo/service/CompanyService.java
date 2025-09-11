package org.example.springbootdemo.service;

import lombok.Getter;
import lombok.Setter;
import org.example.springbootdemo.entity.Company;
import org.example.springbootdemo.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Getter
@Setter
@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company getCompanyById(long id) {
        return companyRepository.getCompanyById(id);
    }

    public List<Company> getCompaniesList() {
        return companyRepository.getAllCompanies();
    }

    public List<Company> getCompaniesByPageAndSize(int page, int size) {
        List<Company> companies = companyRepository.getAllCompanies();
        int fromIndex = Math.max((page - 1) * size, 0);
        int toIndex = Math.min(fromIndex + size, companies.size());
        if (fromIndex > toIndex) {
            return new ArrayList<>();
        }
        return companies.subList(fromIndex, toIndex);
    }

    public Map<String, Long> createCompany(Company company) {
        companyRepository.addCompany(company);
        Map<String, Long> idMap = new HashMap<>();
        idMap.put("id", company.getId());
        return idMap;
    }

    public Company updateCompany(long id, Company updatedCompany) {
        return companyRepository.updateCompanyById(id, updatedCompany);
    }

    public Company deleteCompany(long id) {
        return companyRepository.deleteCompanyById(id);
    }

    public void clearCompaniesList() {
        companyRepository.clear();
    }

}
