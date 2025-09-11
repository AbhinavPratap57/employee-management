package com.codemyth.services;

import com.codemyth.dto.EmployeeDTO;
import java.util.List;

public interface EmployeeService {

	String createEmployee(EmployeeDTO employeeDTO);

	List<EmployeeDTO> getAllEmployees();

	EmployeeDTO getEmployeeById(Long id);

	String updateEmployee(Long id, EmployeeDTO updatedEmployeeDTO);

	String deleteEmployeeById(Long id);

	String deleteAllEmployees();
}
