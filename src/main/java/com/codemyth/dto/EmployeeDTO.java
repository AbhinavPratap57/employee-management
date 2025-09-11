
package com.codemyth.dto;

import java.time.LocalDate;

public class EmployeeDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private int age;
	private LocalDate dateOfBirth;
	private String gender;
	private String address;
	private String city;
	private String phoneNumber;
	private Float salary;
	private String emailAddress;

	public EmployeeDTO() {
	}

	// ParameterizedConstructor
	public EmployeeDTO(Long id, String firstName, String lastName, int age, LocalDate dateOfBirth, String gender,
			String address, String city, String phoneNumber, Float salary, String emailAddress) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.address = address;
		this.city = city;
		this.phoneNumber = phoneNumber;
		this.salary = salary;
		this.emailAddress = emailAddress;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Float getSalary() {
		return salary;
	}

	public void setSalary(Float salary) {
		this.salary = salary;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	public String toString() {
		return "EmployeeDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age
				+ ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", address=" + address + ", city=" + city
				+ ", phoneNumber=" + phoneNumber + ", salary=" + salary + ", emailAddress=" + emailAddress + "]";
	}
}
