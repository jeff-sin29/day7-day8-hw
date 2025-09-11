package org.example.springbootdemo.controller;

import org.example.springbootdemo.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

    @BeforeEach
    void setUp(){
        employeeService.clearEmployeesList();
        employeeService.setIdCounter(0);
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
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void should_Return_Employee_when_Get_Employee_given_employee_id() throws Exception {
        String requestBody = """
                {
                    "name": "John Doe",
                    "age": 30,
                    "salary": 7000,
                    "gender": "Male"
                }
                """;

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        mockMvc.perform(get("/employees/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.salary").value(7000))
                .andExpect(jsonPath("$.gender").value("Male"));
    }

    @Test
    void should_Return_Employees_when_Query_Employee_Given_gender() throws Exception {
        String requestBody = """
                {
                    "name": "John Doe",
                    "age": 30,
                    "salary": 7000,
                    "gender": "Male"
                }
                """;

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        requestBody = """
                {
                    "name": "Mary",
                    "age": 30,
                    "salary": 7000,
                    "gender": "Female"
                }
                """;

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2));

        requestBody = """
                {
                    "name": "Candy",
                    "age": 30,
                    "salary": 8000,
                    "gender": "Female"
                }
                """;

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3));

        mockMvc.perform(get("/employees")
                        .param("gender", "Female")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void should_Update_Employee_Age_And_Salary_given_Put_Request() throws Exception {
        String createRequest = """
            {
                "name": "HelloWorld",
                "age": 30,
                "salary": 5000,
                "gender": "Male"
            }
            """;
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        String updateRequest = """
            {
                "age": 33,
                "salary": 7000
            }
            """;
        mockMvc.perform(put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.age").value(33))
                .andExpect(jsonPath("$.salary").value(7000));
    }

    @Test
    void should_Return_All_Employees_when_GetAllEmployees_given_Employees_Exist() throws Exception {
        String employee1 = """
        {
            "name": "Mary",
            "age": 28,
            "salary": 6000,
            "gender": "Female"
        }
        """;
        String employee2 = """
        {
            "name": "Peter",
            "age": 24,
            "salary": 7000,
            "gender": "Male"
        }
        """;
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee1))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee2))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2));

        mockMvc.perform(get("/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Mary"))
                .andExpect(jsonPath("$[1].name").value("Peter"));
    }

    @Test
    void should_Delete_Employee_and_Return_204_when_Employee_Exists() throws Exception {
        String requestBody = """
        {
            "name": "Ketty",
            "age": 30,
            "salary": 5000,
            "gender": "Female"
        }
        """;
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_Return_Paged_Employees_when_Query_With_Page_And_Size() throws Exception {
        String employee1 = """
    {
        "name": "Katy",
        "age": 25,
        "salary": 5000,
        "gender": "Female"
    }
    """;
        String employee2 = """
    {
        "name": "Kenny",
        "age": 28,
        "salary": 6000,
        "gender": "Male"
    }
    """;
        String employee3 = """
    {
        "name": "Jeff",
        "age": 30,
        "salary": 7000,
        "gender": "Male"
    }
    """;
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee1))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee2))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee3))
                .andExpect(status().isCreated());

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