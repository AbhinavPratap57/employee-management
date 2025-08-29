package com.codemyth.controllers;

import com.codemyth.models.Employee;
import com.codemyth.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
        try {
            String response = employeeService.createEmployee(employee);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.isPresent() ? ResponseEntity.ok(employee.get()) :
                ResponseEntity.status(404).body("❌ Employee not found with id " + id);
    }

 
    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            String response = employeeService.updateEmployee(id, employee);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.deleteEmployeeById(id));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllEmployees() {
        return ResponseEntity.ok(employeeService.deleteAllEmployees());
    }
}
