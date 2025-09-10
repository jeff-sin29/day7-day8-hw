package org.example.springbootdemo.service;

import lombok.Getter;
import lombok.Setter;
import org.example.springbootdemo.entity.Company;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Getter
@Setter
@Service
public class CompanyService {
    private List<Company> companies = new ArrayList<>();

    private int idCounter = 0;

    public Company getCompanyById(long id) {
        return companies.stream()
                .filter(company -> company.getId() == id)
                .findFirst().orElse(null);
    }

    public List<Company> getCompaniesList() {
        return companies;
    }

    public List<Company> getCompaniesByPageAndSize(int page, int size) {
        int fromIndex = Math.max((page - 1) * size, 0);
        int toIndex = Math.min(fromIndex + size, companies.size());
        if (fromIndex > toIndex) {
            return new ArrayList<>();
        }
        return companies.subList(fromIndex, toIndex);
    }

    public Map<String, Integer> createCompany(Company company) {
        int newId = ++idCounter;
        company.setId(newId);
        companies.add(company);
        Map<String, Integer> idMap = new HashMap<>();
        idMap.put("id", newId);
        return idMap;
    }

    public Company updateCompany(long id, Company updatedCompany) {
        for (int i = 0; i < companies.size(); i++) {
            if (companies.get(i).getId() == id) {
                updatedCompany.setId(id);
                companies.set(i, updatedCompany);
                return updatedCompany;
            }
        }
        return null;
    }

    public Company deleteCompany(long id) {
        for (int i = 0; i < companies.size(); i++) {
            if (companies.get(i).getId() == id) {
                return companies.remove(i);
            }
        }
        return null;
    }


}
