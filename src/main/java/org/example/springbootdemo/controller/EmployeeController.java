package org.example.springbootdemo.controller;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import org.example.springbootdemo.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {
    public List<Employee> employees = new ArrayList<>();

    private int idCounter = 0;

    @PostMapping("/employees")
    public ResponseEntity<Map<String, Object>> createEmployee(@RequestBody Employee employee){
        idCounter ++;
        employee.setId(idCounter);
        employees.add(employee);
        return ResponseEntity.status(201).body(Map.of("id", employee.getId()));
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable long id){
        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @GetMapping("/employees")
    public List<Employee> queryEmployeeByGender(@RequestParam String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender() != null && employee.getGender().equalsIgnoreCase(gender))
                .toList();
    }







}
