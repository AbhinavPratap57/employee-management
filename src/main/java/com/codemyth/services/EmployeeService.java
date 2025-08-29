package com.codemyth.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemyth.models.Employee;
import com.codemyth.repository.EmployeeRepository;
//Service Class
@Service
public class EmployeeService {

 @Autowired
 private EmployeeRepository employeeRepository;

 // ✅ Create Employee
 public String createEmployee(Employee employee) {
     validateEmployeeFields(employee); // सिर्फ fields validate
     checkDuplicateEmployee(employee); // duplicate check
     employeeRepository.save(employee);
     return "✅ Employee created successfully!";
 }

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	public Optional<Employee> getEmployeeById(Long id) {
		return employeeRepository.findById(id);
	}
 

 // ✅ Update Employee
 public String updateEmployee(Long id, Employee updatedEmployee) {
     Optional<Employee> existing = employeeRepository.findById(id);

     if (existing.isPresent()) {
         Employee emp = existing.get();

         // 🔹 Duplicate check (ignore current employee id)
         if (employeeRepository.existsByEmailAddressAndIdNot(updatedEmployee.getEmailAddress(), id)) {
             throw new IllegalArgumentException("❌ Email already exists for another employee");
         }
         if (employeeRepository.existsByPhoneNumberAndIdNot(updatedEmployee.getPhoneNumber(), id)) {
             throw new IllegalArgumentException("❌ Phone number already exists for another employee");
         }
         if (employeeRepository.existsByFirstNameAndLastNameAndIdNot(
                 updatedEmployee.getFirstName(),
                 updatedEmployee.getLastName(),
                 id)) {
             throw new IllegalArgumentException("❌ Employee with same name already exists");
         }

         // 🔹 Update fields
         emp.setFirstName(updatedEmployee.getFirstName());
         emp.setLastName(updatedEmployee.getLastName());
         emp.setAge(updatedEmployee.getAge());
         emp.setDateOfBirth(updatedEmployee.getDateOfBirth());
         emp.setGender(updatedEmployee.getGender());
         emp.setAddress(updatedEmployee.getAddress());
         emp.setCity(updatedEmployee.getCity());
         emp.setPhoneNumber(updatedEmployee.getPhoneNumber());
         emp.setSalary(updatedEmployee.getSalary());
         emp.setEmailAddress(updatedEmployee.getEmailAddress());

         // ✅ सिर्फ field validations (duplicate नहीं)
         validateEmployeeFields(emp);

         employeeRepository.save(emp);
         return "✅ Employee updated successfully!";
     } else {
         return "❌ Employee not found with id " + id;
     }
 }
	public String deleteEmployeeById(Long id) {
		if (employeeRepository.existsById(id)) {
			employeeRepository.deleteById(id);
			return "✅ Employee deleted successfully!";
		}
		return "❌ Employee not found with id " + id;
	}

	public String deleteAllEmployees() {
		employeeRepository.deleteAll();
		return "✅ All employees deleted successfully!";
	}
 // 🔹 सिर्फ field validation
 private void validateEmployeeFields(Employee employee) {
     if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty()) {
         throw new IllegalArgumentException("❌ First name is required");
     }

     if (employee.getLastName() == null || employee.getLastName().trim().isEmpty()) {
         throw new IllegalArgumentException("❌ Last name is required");
     }

     // ✅ Age validation
     if (employee.getDateOfBirth() != null) {
         int age = Period.between(employee.getDateOfBirth(), LocalDate.now()).getYears();
         if (age != employee.getAge()) {
             throw new IllegalArgumentException("❌ Age does not match with Date of Birth");
         }
     }

     // ✅ PhoneNumber validation
     if (employee.getPhoneNumber() != null && !employee.getPhoneNumber().matches("\\d{10}")) {
         throw new IllegalArgumentException("❌ Phone number must be 10 digits");
     }

     // ✅ Email validation
     if (employee.getEmailAddress() != null
             && !Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", employee.getEmailAddress())) {
         throw new IllegalArgumentException("❌ Invalid email format");
     }

     // ✅ Gender validation
     if (employee.getGender() == null || !(employee.getGender().equalsIgnoreCase("Male")
             || employee.getGender().equalsIgnoreCase("Female") || employee.getGender().equalsIgnoreCase("Other"))) {
         throw new IllegalArgumentException("❌ Gender must be Male, Female or Other");
     }

     // ✅ Address validation
     if (employee.getAddress() == null || employee.getAddress().length() < 10) {
         throw new IllegalArgumentException("❌ Address must be at least 10 characters long");
     }

     // ✅ City validation
     if (employee.getCity() == null || !employee.getCity().matches("^[A-Za-z ]{2,50}$")) {
         throw new IllegalArgumentException("❌ City must contain only alphabets (2-50 characters)");
     }

     // ✅ Salary validation
     if (employee.getSalary() == null || employee.getSalary() < 1000) {
         throw new IllegalArgumentException("❌ Salary must be at least 1000");
     }
 }

 // 🔹 सिर्फ create के लिए duplicate check
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
