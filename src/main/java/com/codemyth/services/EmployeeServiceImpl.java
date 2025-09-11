package com.codemyth.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemyth.dto.EmployeeDTO;
import com.codemyth.exceptions.EmployeeNotFoundException;
import com.codemyth.mapper.EmployeeMapper;
import com.codemyth.models.Employee;
import com.codemyth.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	// CreateEmployee
	@Override
	public String createEmployee(EmployeeDTO employeeDTO) {
		try {
			Employee employee = EmployeeMapper.toEntity(employeeDTO);

			validateEmployeeFields(employee);
			checkDuplicateEmployee(employee);
			employeeRepository.save(employee);
			return "✅ Employee created successfully!";
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		} catch (Exception e) {
			return "❌ Something went wrong while creating employee";
		}
	}

	// GetAllEmployees
	@Override
	public List<EmployeeDTO> getAllEmployees() {
		try {
			return employeeRepository.findAll().stream().map(EmployeeMapper::toDTO).collect(Collectors.toList());
		} catch (Exception e) {
			throw new RuntimeException("❌ Failed to fetch employees", e);
		}
	}

     // GetEmployeeById
	@Override
	public EmployeeDTO getEmployeeById(Long id) {
		Employee emp = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("❌ Employee not found with id " + id));
		return EmployeeMapper.toDTO(emp);
	}

	// UpdateEmployee
	@Override
	public String updateEmployee(Long id, EmployeeDTO updatedEmployeeDTO) {
		try {
			Optional<Employee> existing = employeeRepository.findById(id);
			if (existing.isEmpty()) {
				return "❌ Employee not found with id " + id;
			}
			Employee emp = existing.get();

			if (employeeRepository.existsByEmailAddressAndIdNot(updatedEmployeeDTO.getEmailAddress(), id)) {
				throw new IllegalArgumentException("❌ Email already exists for another employee");
			}
			if (employeeRepository.existsByPhoneNumberAndIdNot(updatedEmployeeDTO.getPhoneNumber(), id)) {
				throw new IllegalArgumentException("❌ Phone number already exists for another employee");
			}
			if (employeeRepository.existsByFirstNameAndLastNameAndIdNot(updatedEmployeeDTO.getFirstName(),
					updatedEmployeeDTO.getLastName(), id)) {
				throw new IllegalArgumentException("❌ Employee with same name already exists");
			}
			
            Employee updatedEmp = EmployeeMapper.toEntity(updatedEmployeeDTO);
            updatedEmp.setId(id);
            
            validateEmployeeFields(updatedEmp);
            employeeRepository.save(updatedEmp);

			return "✅ Employee updated successfully!";
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		} catch (Exception e) {
			return "❌ Something went wrong while updating employee";
		}
	}
    // DeleteEmployeeById
	@Override
	public String deleteEmployeeById(Long id) {
		try {
			if (employeeRepository.existsById(id)) {
				employeeRepository.deleteById(id);
				return "✅ Employee deleted successfully!";
			}
			return "❌ Employee not found with id " + id;
		} catch (Exception e) {
			return "❌ Something went wrong while deleting employee";
		}
	}
     //DeleteAllEmployees
	@Override
	public String deleteAllEmployees() {
		try {
			employeeRepository.deleteAll();
			return "✅ All employees deleted successfully!";
		} catch (Exception e) {
			return "❌ Something went wrong while deleting all employees";
		}
	}

	// Validation
	private void validateEmployeeFields(Employee employee) {
		if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty()) {
			throw new IllegalArgumentException("❌ First name is required");
		}

		if (employee.getLastName() == null || employee.getLastName().trim().isEmpty()) {
			throw new IllegalArgumentException("❌ Last name is required");
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
		if (employee.getAddress() == null || employee.getAddress().length() < 10) {
			throw new IllegalArgumentException("❌ Address must be at least 10 characters long");
		}

		// CityValidation
		if (employee.getCity() == null || !employee.getCity().matches("^[A-Za-z ]{2,50}$")) {
			throw new IllegalArgumentException("❌ City must contain only alphabets (2-50 characters)");
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
