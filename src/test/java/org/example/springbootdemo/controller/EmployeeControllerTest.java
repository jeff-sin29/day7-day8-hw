package org.example.springbootdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springbootdemo.entity.Company;
import org.example.springbootdemo.entity.Employee;
import org.example.springbootdemo.repository.CompanyRepository;
import org.example.springbootdemo.repository.EmployeeRepository;
import org.example.springbootdemo.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.apache.el.lang.ELArithmetic.isNumber;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    void setUp(){
        employeeService.clearEmployeesList();
    }

    private long createEmployee(String requestBody) throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        return new ObjectMapper().readTree(contentAsString).get("id").asLong();
    }

    @Test
    void should_Create_Employee_given_employee_correct_attribute() throws Exception{
        String requestBody = """
                {
                    "name": "John Doe",
                    "age": 30,
                    "salary": 5000,
                    "gender": "Male"
                }
                """;
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    void should_Return_Employee_when_Get_Employee_given_employee_id() throws Exception {
        Company company= new Company();
        company.setName("oocl");
        companyRepository.addCompany(company);
        Employee employee = new Employee("John Doe", 30, 7000, "Male");
        employee.setStatus(true);
        employee.setCompanyId(company.getId());
        employeeRepository.addEmployee(employee);

        mockMvc.perform(get("/employees/{id}", employee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.salary").value(7000))
                .andExpect(jsonPath("$.gender").value("Male"));
    }

    @Test
    void should_Return_Employees_when_Query_Employee_Given_gender() throws Exception {
        Company company= new Company();
        company.setName("oocl");
        companyRepository.addCompany(company);
        Employee employee = new Employee("John Doe", 30, 7000, "Male");
        employee.setStatus(true);
        employee.setCompanyId(company.getId());
        employeeRepository.addEmployee(employee);

        Employee employee2 = new Employee("Mary", 30, 7000, "Female");
        employee2.setStatus(true);
        employee2.setCompanyId(company.getId());
        employeeRepository.addEmployee(employee2);

        Employee employee3 = new Employee("Candy", 30, 8000, "Female");
        employee3.setStatus(true);
        employee3.setCompanyId(company.getId());
        employeeRepository.addEmployee(employee3);

        mockMvc.perform(get("/employees")
                        .param("gender", "Female")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void should_Update_Employee_Age_And_Salary_given_Put_Request() throws Exception {
        Company company= new Company();
        company.setName("oocl");
        companyRepository.addCompany(company);
        Employee employee = new Employee("HelloWorld", 30, 500, "Male");
        employee.setStatus(true);
        employee.setCompanyId(company.getId());
        employeeRepository.addEmployee(employee);

        String updateRequest = """
            {   
                "name": "HelloWorld",
                "age": 28,
                "salary": 7000
            }
            """;
        mockMvc.perform(put("/employees/{id}", employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.age").value(28))
                .andExpect(jsonPath("$.salary").value(7000));
    }

    @Test
    void should_Return_All_Employees_when_GetAllEmployees_given_Employees_Exist() throws Exception {

        Company company= new Company();
        company.setName("oocl");
        companyRepository.addCompany(company);
        Employee employee = new Employee("Mary", 28, 6000, "Female");
        employee.setStatus(true);
        employee.setCompanyId(company.getId());
        employeeRepository.addEmployee(employee);

        Employee employee2 = new Employee("Peter", 24, 7000, "Male");
        employee2.setStatus(true);
        employee2.setCompanyId(company.getId());
        employeeRepository.addEmployee(employee2);

        mockMvc.perform(get("/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Mary"))
                .andExpect(jsonPath("$[1].name").value("Peter"));
    }

    @Test
    void should_Delete_Employee_and_Return_204_when_Employee_Exists() throws Exception {
        Company company= new Company();
        company.setName("oocl");
        companyRepository.addCompany(company);
        Employee employee = new Employee("Ketty", 30, 5000, "Female");
        employee.setStatus(true);
        employee.setCompanyId(company.getId());
        employeeRepository.addEmployee(employee);

        mockMvc.perform(delete("/employees/{id}", employee.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_Return_Paged_Employees_when_Query_With_Page_And_Size() throws Exception {
        Company company= new Company();
        company.setName("oocl");
        companyRepository.addCompany(company);
        Employee employee = new Employee("Katy", 25, 5000, "Female");
        employee.setStatus(true);
        employee.setCompanyId(company.getId());
        employeeRepository.addEmployee(employee);

        Employee employee2 = new Employee("Kenny", 28, 6000, "Male");
        employee2.setStatus(true);
        employee2.setCompanyId(company.getId());
        employeeRepository.addEmployee(employee2);

        Employee employee3 = new Employee("Jeff", 30, 7000, "Male");
        employee3.setStatus(true);
        employee3.setCompanyId(company.getId());
        employeeRepository.addEmployee(employee3);

        mockMvc.perform(get("/employees")
                        .param("page", "1")
                        .param("size", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Katy"))
                .andExpect(jsonPath("$[1].name").value("Kenny"));
    }

    @Test
    void should_Return_400_when_Create_Employee_Given_Age_Below_18() throws Exception {

        String requestBody = """
                {
                    "name": "Mary",
                    "age": 17,
                    "salary": 5000,
                    "gender": "Female"
                }
                """;
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_Return_400_when_Create_Employee_Given_Age_Above_65() throws Exception {
        String requestBody = """
                {
                    "name": "Ken",
                    "age": 66,
                    "salary": 7000,
                    "gender": "Male"
                }
                """;
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

}