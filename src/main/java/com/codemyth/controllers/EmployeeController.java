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
	public ResponseEntity<ApiResponse<EmployeeDTO>> createEmployee(@Valid@RequestBody EmployeeDTO employeeDTO) {
		String response = employeeService.createEmployee(employeeDTO);
		if (response.startsWith("❌")) {
			return ResponseEntity.badRequest().body(new ApiResponse<>(false, response, null));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, response, employeeDTO));
	}

	// GetAllEmployees
	@GetMapping
	public ResponseEntity<ApiResponse<List<EmployeeDTO>>> getAllEmployees() {
		List<EmployeeDTO> employees = employeeService.getAllEmployees();
		return ResponseEntity.ok(new ApiResponse<>(true, "✅ Employees fetched successfully", employees));
	}

	// GetEmployeeByID
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<EmployeeDTO>> getEmployeeById(@PathVariable Long id) {
		EmployeeDTO emp = employeeService.getEmployeeById(id);
		return ResponseEntity.ok(new ApiResponse<>(true, "✅ Employee fetched successfully", emp));
	}

	// UpdateEmployee
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<EmployeeDTO>> updateEmployee(@PathVariable Long id,
			@Valid @RequestBody EmployeeDTO employeeDTO) {
		String response = employeeService.updateEmployee(id, employeeDTO);
		if (response.startsWith("❌")) {
			return ResponseEntity.badRequest().body(new ApiResponse<>(false, response, null));
		}
		return ResponseEntity.ok(new ApiResponse<>(true, response, employeeDTO));
	}

	// DeleteByID
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Long id) {
		String response = employeeService.deleteEmployeeById(id);
		if (response.startsWith("❌")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, response, null));
		}
		return ResponseEntity.ok(new ApiResponse<>(false, response, null));
	}

	// DeleteAllEmployees
	@DeleteMapping
	public ResponseEntity<ApiResponse<Void>> deleteAllEmployees() {
		String response = employeeService.deleteAllEmployees();
		return ResponseEntity.ok(new ApiResponse<>(true, response, null));
	}
}
