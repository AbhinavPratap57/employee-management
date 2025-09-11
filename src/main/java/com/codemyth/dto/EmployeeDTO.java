
package com.codemyth.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}