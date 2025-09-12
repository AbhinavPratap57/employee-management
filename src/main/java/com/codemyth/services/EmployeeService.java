package com.codemyth.services;

import java.util.List;

import com.codemyth.dto.EmployeeDTO;
import com.codemyth.models.Employee;
import com.codemyth.payload.ApiResponse;

public interface EmployeeService {
    ApiResponse<Employee> createEmployee(EmployeeDTO employeeDTO);
    ApiResponse<List<EmployeeDTO>> getAllEmployees();
    ApiResponse<EmployeeDTO> getEmployeeById(Long id);
    ApiResponse<Employee> updateEmployee(Long id, EmployeeDTO employeeDTO);
    ApiResponse<Void> deleteEmployeeById(Long id);
    ApiResponse<Void> deleteAllEmployees();
}