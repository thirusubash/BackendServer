package com.gksvp.web.company.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SelectCompanyEmployeeDTO {
    private Long id;


    private String firstName;


    private String lastName;


    private String employeeCode;


    private Date hireDate;


    private String Email;
    private String mobileNumber;
}
