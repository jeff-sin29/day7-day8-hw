package org.example.springbootdemo.controller;

import org.example.springbootdemo.Company;
import org.example.springbootdemo.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/companies")
    public ResponseEntity<Map<String, Integer>> createCompany(@RequestBody Company company) {
        int newId = ++idCounter;
        company.setId(newId);
        companies.add(company);
        Map<String, Integer> response = new HashMap<>();
        response.put("id", newId);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/companies/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable int id, @RequestBody Company updatedCompany) {
        for (Company company : companies) {
            if (company.getId() == id) {
                company.setName(updatedCompany.getName());
                return ResponseEntity.ok(company);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable int id) {
        for (int i = 0; i < companies.size(); i++) {
            if (companies.get(i).getId() == id) {
                companies.remove(i);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.notFound().build();
    }


}
