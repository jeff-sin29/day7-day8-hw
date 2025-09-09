package org.example.springbootdemo.controller;

import org.example.springbootdemo.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyController companyController;

    @BeforeEach
    void setUp() {
        companyController.setCompanies(new ArrayList<>());
        companyController.setIdCounter(0);
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1, "Company1"));
        companies.add(new Company(2, "Company2"));
        companyController.setCompanies(companies);
    }

    @Test
    void should_Return_All_Companies_when_GetCompanies() throws Exception {
        mockMvc.perform(get("/companies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Company1"))
                .andExpect(jsonPath("$[1].name").value("Company2"));
    }
}