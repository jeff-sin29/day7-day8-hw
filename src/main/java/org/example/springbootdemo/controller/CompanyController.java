package org.example.springbootdemo.controller;

import org.example.springbootdemo.Company;
import org.example.springbootdemo.Employee;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/companies")
    public List<Company> getCompanies() {
        return companies;
    }
}
