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
import com.codemyth.models.Employee;
import com.codemyth.payload.ApiResponse;
import com.codemyth.services.EmployeeServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	@Autowired
	private EmployeeServiceImpl employeeService;

	// CreateEmployee
	@PostMapping
	public ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
		ApiResponse<Employee> response = employeeService.createEmployee(employeeDTO);

		if (response.isSuccess()) {
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	// GetAllEmployees
	@GetMapping
	public ResponseEntity<ApiResponse<List<EmployeeDTO>>> getAllEmployees() {
		ApiResponse<List<EmployeeDTO>> response = employeeService.getAllEmployees();
		return ResponseEntity.ok(response);
	}

	// GetEmployeeByID
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<EmployeeDTO>> getEmployeeById(@PathVariable Long id) {
		ApiResponse<EmployeeDTO> response = employeeService.getEmployeeById(id);

		if (response.isSuccess()) {
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

	// UpdateEmployee
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Employee>> updateEmployee(@PathVariable Long id,
			@Valid @RequestBody EmployeeDTO employeeDTO) {
		ApiResponse<Employee> response = employeeService.updateEmployee(id, employeeDTO);

		if (response.isSuccess()) {
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.badRequest().body(response);
		}
	}

	// DeleteByID
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Long id) {
		ApiResponse<Void> response = employeeService.deleteEmployeeById(id);

		if (response.isSuccess()) {
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

	// DeleteAllEmployees
	@DeleteMapping
	public ResponseEntity<ApiResponse<Void>> deleteAllEmployees() {
		ApiResponse<Void> response = employeeService.deleteAllEmployees();
		return ResponseEntity.ok(response);
	}
}