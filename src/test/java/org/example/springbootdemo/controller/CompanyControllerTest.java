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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1, "Company1"));
        companies.add(new Company(2, "Company2"));
        companyController.setCompanies(companies);
        companyController.setIdCounter(2);
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

    @Test
    void should_Return_Company_when_GetCompanyById_given_CompanyId() throws Exception {
        mockMvc.perform(get("/companies/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Company1"));
    }

    @Test
    void should_Return_404_when_GetCompanyById_given_NonexistentId() throws Exception {
        mockMvc.perform(get("/companies/{id}", 924)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_Return_Paged_Companies_when_Query_With_Page_And_Size() throws Exception {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1, "Company1"));
        companies.add(new Company(2, "Company2"));
        companies.add(new Company(3, "Company3"));
        companyController.setCompanies(companies);

        mockMvc.perform(get("/companies")
                        .param("page", "1")
                        .param("size", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Company1"))
                .andExpect(jsonPath("$[1].name").value("Company2"));
    }

    @Test
    void should_Create_Company_and_Return_Id_given_Valid_Company() throws Exception {
        String requestBody = """
    {
        "name": "Company3"
    }
    """;
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    void should_Update_Company_Name_given_Valid_Id() throws Exception {
        String updateRequest = """
        {
            "name": "Companyyy"
        }
        """;
        mockMvc.perform(put("/companies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Companyyy"));
    }

    @Test
    void should_Return_404_when_Update_Company_given_Nonexistent_Id() throws Exception {
        String updateRequest = """
        {
            "name": "failll"
        }
        """;
        mockMvc.perform(put("/companies/543")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequest))
                .andExpect(status().isNotFound());
    }

}