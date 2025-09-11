package com.codemyth.mapper;

import com.codemyth.dto.EmployeeDTO;
import com.codemyth.models.Employee;

public class EmployeeMapper {

    // Entity → DTO
    public static EmployeeDTO toDTO(Employee emp) {
        if (emp == null) return null;

        return EmployeeDTO.builder()
                .id(emp.getId())
                .firstName(emp.getFirstName())
                .lastName(emp.getLastName())
                .age(emp.getAge())
                .dateOfBirth(emp.getDateOfBirth())
                .gender(emp.getGender())
                .address(emp.getAddress())
                .city(emp.getCity())
                .phoneNumber(emp.getPhoneNumber())
                .salary(emp.getSalary())
                .emailAddress(emp.getEmailAddress())
                .build();
    }

    // DTO → Entity
    public static Employee toEntity(EmployeeDTO dto) {
        if (dto == null) return null;

        return Employee.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .age(dto.getAge())
                .dateOfBirth(dto.getDateOfBirth())
                .gender(dto.getGender())
                .address(dto.getAddress())
                .city(dto.getCity())
                .phoneNumber(dto.getPhoneNumber())
                .salary(dto.getSalary())
                .emailAddress(dto.getEmailAddress())
                .build();
    }
}
