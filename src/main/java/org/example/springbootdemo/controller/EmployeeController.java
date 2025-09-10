package org.example.springbootdemo.controller;

import lombok.Getter;
import lombok.Setter;
import org.example.springbootdemo.entity.Employee;
import org.example.springbootdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/employees")
    public ResponseEntity<Map<String, Object>> createEmployee(@RequestBody Employee employee){
        Map<String, Object> response = employeeService.createEmployee(employee);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable long id){
        return ResponseEntity.status(200).body(employeeService.getEmployeeById(id));
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees(
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (gender != null) {
            List<Employee> filteredEmployees = employeeService.getEmployeesByGender(gender);
            return ResponseEntity.ok(filteredEmployees);
        }
        else if (page != null && size != null) {
            List<Employee> filteredEmployees = employeeService.getEmployeesByPageAndSize(page, size);
            return ResponseEntity.ok(filteredEmployees);
        }
        else{
            return ResponseEntity.ok(employeeService.getEmployees());
        }

    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee updatedEmployee) {
        Employee employee = employeeService.updateEmployee(id, updatedEmployee);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
        boolean removed = employeeService.deleteEmployee(id);
        if (removed) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
