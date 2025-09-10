package org.example.springbootdemo.repository;

import lombok.Getter;
import lombok.Setter;
import org.example.springbootdemo.entity.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Repository
public class CompanyRepository {
    private List<Company> companies = new ArrayList<>();

    public Company getCompanyById(long id) {
        return companies.stream()
                .filter(company -> company.getId() == id)
                .findFirst().orElse(null);
    }

    public List<Company> getAllCompanies() {
        return companies;
    }

    public void addCompany(Company company) {
        companies.add(company);
    }

    public Company updateCompanyById(long id, Company updatedCompany) {
        for (int i = 0; i < companies.size(); i++) {
            if (companies.get(i).getId() == id) {
                updatedCompany.setId(id);
                companies.set(i, updatedCompany);
                return updatedCompany;
            }
        }
        return null;
    }

    public Company deleteCompanyById(long id) {
        for (int i = 0; i < companies.size(); i++) {
            if (companies.get(i).getId() == id) {
                return companies.remove(i);
            }
        }
        return null;
    }
}
