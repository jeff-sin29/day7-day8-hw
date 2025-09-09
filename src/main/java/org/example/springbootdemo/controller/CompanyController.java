package org.example.springbootdemo.controller;

import org.example.springbootdemo.Company;
import org.example.springbootdemo.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CompanyController {
    private List<Company> companies = new ArrayList<>();

    private int idCounter = 0;

    public void setIdCounter(int idCounter) {
        this.idCounter = idCounter;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public int getIdCounter() {
        return idCounter;
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable int id) {
        return companies.stream()
                .filter(company -> company.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/companies")
    public List<Company> getCompanies(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page != null && size != null) {
            int fromIndex = Math.max((page - 1) * size, 0);
            int toIndex = Math.min(fromIndex + size, companies.size());
            if (fromIndex > toIndex) {
                return new ArrayList<>();
            }
            return companies.subList(fromIndex, toIndex);
        }
        return companies;
    }
}
