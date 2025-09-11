package com.codemyth.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemyth.dto.EmployeeDTO;
import com.codemyth.services.EmployeeServiceImpl;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	@Autowired
	private EmployeeServiceImpl employeeService;

	// CreateEmployee
	@PostMapping
	public ResponseEntity<String> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
		String response = employeeService.createEmployee(employeeDTO);
		if (response.startsWith("❌")) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	// GetAllEmployees
	@GetMapping
	public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
		return ResponseEntity.ok(employeeService.getAllEmployees());
	}

	// GetEmployeeByID
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
		return ResponseEntity.ok(employeeService.getEmployeeById(id));
	}

	// UpdateEmployee
	@PutMapping("/{id}")
	public ResponseEntity<String> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
		String response = employeeService.updateEmployee(id, employeeDTO);
		if (response.startsWith("❌")) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	// DeleteByID
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
		String response = employeeService.deleteEmployeeById(id);
		if (response.startsWith("❌")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		return ResponseEntity.ok(response);
	}

	// DeleteAllEmployees
	@DeleteMapping
	public ResponseEntity<String> deleteAllEmployees() {
		String response = employeeService.deleteAllEmployees();
		return ResponseEntity.ok(response);
	}
}
