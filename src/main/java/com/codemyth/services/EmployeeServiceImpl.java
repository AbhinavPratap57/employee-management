package com.codemyth.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemyth.dto.EmployeeDTO;
import com.codemyth.exceptions.EmployeeNotFoundException;
import com.codemyth.models.Employee;
import com.codemyth.payload.ApiResponse;
import com.codemyth.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ModelMapper modelMapper;

	// CreateEmployee
	@Override
	public ApiResponse<Employee> createEmployee(EmployeeDTO employeeDTO) {
		try {
			Employee employee = modelMapper.map(employeeDTO, Employee.class);

			validateEmployeeFields(employee);
			checkDuplicateEmployee(employee);

			Employee savedEmployee = employeeRepository.save(employee);
			return ApiResponse.success("✅ Employee created successfully!", savedEmployee);
		} catch (IllegalArgumentException e) {
			return ApiResponse.error(e.getMessage());
		} catch (Exception e) {
			return ApiResponse.error("❌ Something went wrong while creating employee");
		}
	}

	// GetAllEmployees
	@Override
	public ApiResponse<List<EmployeeDTO>> getAllEmployees() {
		try {
			List<EmployeeDTO> employees = employeeRepository.findAll().stream()
					.map(emp -> modelMapper.map(emp, EmployeeDTO.class)).collect(Collectors.toList());

			return ApiResponse.success("✅ Employees fetched successfully!", employees);
		} catch (Exception e) {
			return ApiResponse.error("❌ Failed to fetch employees");
		}
	}

	// GetEmployeeById
	@Override
	public ApiResponse<EmployeeDTO> getEmployeeById(Long id) {
		try {
			Employee emp = employeeRepository.findById(id)
					.orElseThrow(() -> new EmployeeNotFoundException("❌ Employee not found with id " + id));
			EmployeeDTO empDTO = modelMapper.map(emp, EmployeeDTO.class);

			return ApiResponse.success("✅ Employee fetched successfully!", empDTO);
		} catch (EmployeeNotFoundException e) {
			return ApiResponse.error(e.getMessage());
		}
	}

	// UpdateEmployee
	@Override
	public ApiResponse<Employee> updateEmployee(Long id, EmployeeDTO updatedEmployeeDTO) {
		try {
			Optional<Employee> existing = employeeRepository.findById(id);
			if (existing.isEmpty()) {
				return ApiResponse.error("❌ Employee not found with id " + id);
			}

			Employee updatedEmp = modelMapper.map(updatedEmployeeDTO, Employee.class);
			updatedEmp.setId(id);

			validateEmployeeFields(updatedEmp);
			Employee saved = employeeRepository.save(updatedEmp);

			return ApiResponse.success("✅ Employee updated successfully!", saved);
		} catch (IllegalArgumentException e) {
			return ApiResponse.error(e.getMessage());
		} catch (Exception e) {
			return ApiResponse.error("❌ Something went wrong while updating employee");
		}
	}

	// DeleteEmployeeById
	@Override
	public ApiResponse<Void> deleteEmployeeById(Long id) {
		try {
			if (employeeRepository.existsById(id)) {
				employeeRepository.deleteById(id);
				return ApiResponse.success("✅ Employee deleted successfully!", null);
			}
			return ApiResponse.error("❌ Employee not found with id " + id);
		} catch (Exception e) {
			return ApiResponse.error("❌ Something went wrong while deleting employee");
		}
	}

	// DeleteAllEmployees
	@Override
	public ApiResponse<Void> deleteAllEmployees() {
		try {
			employeeRepository.deleteAll();
			return ApiResponse.success("✅ All employees deleted successfully!", null);
		} catch (Exception e) {
			return ApiResponse.error("❌ Something went wrong while deleting all employees");
		}
	}

	// Validation
	private void validateEmployeeFields(Employee employee) {
		if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty()
				|| employee.getFirstName().equalsIgnoreCase("null")) {
			throw new IllegalArgumentException("❌ First name is required and cannot be 'null");
		}

		if (employee.getLastName() == null || employee.getLastName().trim().isEmpty()
				|| employee.getLastName().equalsIgnoreCase("null")) {
			throw new IllegalArgumentException("❌ Last name is required and cannot be 'null");
		}

		// AgeValidation
		if (employee.getDateOfBirth() != null) {
			int age = Period.between(employee.getDateOfBirth(), LocalDate.now()).getYears();
			if (age != employee.getAge()) {
				throw new IllegalArgumentException("❌ Age does not match with Date of Birth");
			}
		}

		// PhoneNumberValidation
		if (employee.getPhoneNumber() != null && !employee.getPhoneNumber().matches("\\d{10}")) {
			throw new IllegalArgumentException("❌ Phone number must be 10 digits");
		}

		// EmailValidation
		if (employee.getEmailAddress() != null
				&& !Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", employee.getEmailAddress())) {
			throw new IllegalArgumentException("❌ Invalid email format");
		}

		// GenderValidation
		if (employee.getGender() == null || !(employee.getGender().equalsIgnoreCase("Male")
				|| employee.getGender().equalsIgnoreCase("Female") || employee.getGender().equalsIgnoreCase("Other"))) {
			throw new IllegalArgumentException("❌ Gender must be Male, Female or Other");
		}

		// AddressValidation
		if (employee.getAddress() == null || employee.getAddress().equalsIgnoreCase("null")
				|| employee.getAddress().length() < 10) {
			throw new IllegalArgumentException("❌ Address must be at least 10 characters long and not 'null");
		}

		// CityValidation
		if (employee.getCity() == null || employee.getCity().equalsIgnoreCase("null")
				|| !employee.getCity().matches("^[A-Za-z ]{2,50}$")) {
			throw new IllegalArgumentException("❌ City must contain only alphabets (2-50 characters) and not 'null");
		}

		// SalaryValidation
		if (employee.getSalary() == null || employee.getSalary() < 1000) {
			throw new IllegalArgumentException("❌ Salary must be at least 1000");
		}
	}

	// DuplicateCheck
	private void checkDuplicateEmployee(Employee employee) {
		if (employeeRepository.existsByFirstNameAndLastName(employee.getFirstName(), employee.getLastName())) {
			throw new IllegalArgumentException("❌ Employee with same name already exists");
		}
		if (employeeRepository.existsByPhoneNumber(employee.getPhoneNumber())) {
			throw new IllegalArgumentException("❌ Phone number already exists");
		}
		if (employeeRepository.existsByEmailAddress(employee.getEmailAddress())) {
			throw new IllegalArgumentException("❌ Email address already exists");
		}
	}
}