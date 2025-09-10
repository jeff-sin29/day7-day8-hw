package org.example.springbootdemo.controller;

import lombok.Getter;
import lombok.Setter;
import org.example.springbootdemo.entity.Company;
import org.example.springbootdemo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@RestController
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable int id) {
        Company company = companyService.getCompanyById(id);
        if (company != null) {
            return ResponseEntity.ok(company);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/companies")
    public List<Company> getCompanies(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (page != null && size != null) {
            return companyService.getCompaniesByPageAndSize(page, size);
        }
        return companyService.getCompaniesList();
    }

    @PostMapping("/companies")
    public ResponseEntity<Map<String, Integer>> createCompany(@RequestBody Company company) {
        Map<String, Integer> response = companyService.createCompany(company);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/companies/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable int id, @RequestBody Company updatedCompany) {
        if (companyService.updateCompany(id, updatedCompany) != null) {
            return ResponseEntity.ok(updatedCompany);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable int id) {
        if (companyService.deleteCompany(id) != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
