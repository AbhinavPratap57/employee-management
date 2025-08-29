package com.codemyth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codemyth.models.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmailAddress(String emailAddress);

    boolean existsByEmailAddressAndIdNot(String emailAddress, Long id);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);
    boolean existsByFirstNameAndLastNameAndIdNot(String firstName, String lastName, Long id);
}
