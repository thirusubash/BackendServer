package com.gksvp.web.company.dto;



import com.gksvp.web.company.entity.Plant;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class EmployeeDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String employeeCode;
    private String email;
    private String mobileNumber;
    private double salary;
    private boolean status;
    private LocalDate hireDate;
    private LocalDate dateOfBirth;
    private String gender;
    private String nationality;
    private String maritalStatus;
    private String address;
    private String emergencyContact;
    private String employmentType;
    private String reportingTo;
    private String education;
    private String skills;
    private String certifications;
    private String backgroundCheck;
    private DesignationDTO designation;   // A DTO representation of Designation entity
    private Long companyId;  // Just the ID since the DTO doesn't need the full company data
    private Plant plant;    // Just the ID since the DTO doesn't need the full plant data
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private List<CompanyRoleDTO> roles;    // List of role IDs associated with the employee
    private List<CompanyGroupDTO> groups;   // List of group IDs associated with the employee

    // Depending on your use case, you might want to include other DTOs or fields.
}