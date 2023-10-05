package com.gksvp.web.company.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gksvp.web.company.entity.*;
import com.gksvp.web.products.dto.BasicProductDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private Long id;
    private String companyId;
    private String password;
    private String companyName;
    private String businessType;
    private String registrationNumber;
    private String registrationAuthority;
    private Date dateOfIncorporation;
    private String businessActivities;
    private String industryClassification;
    private String financialStatements;
    private String contactNumber;
    private String email;
    private String llpNumber;
    private String trademarkAndIP;
    private String insuranceInformation;
    private String tradeLicenseNumber;
    private String healthPermitNumber;
    private String gstin;
    private String tin;
    private Boolean status;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    @JsonIgnoreProperties("company")
    private List<CompanyAddress> companyAddresses;

    @JsonIgnoreProperties("company")
    private List<Plant> plants;

    @JsonIgnoreProperties("company")
    private List<Employee> employees;

    @JsonIgnoreProperties("companies")
    private List<Supplier> suppliers;

    @JsonIgnoreProperties("company")
    private List<CompanyLocation> locations;

    @JsonIgnoreProperties("company")
    private Set<BankDetails> bankAccounts = new HashSet<>();
    @JsonIgnoreProperties({"company"})
    private List<BasicProductDTO> products;
    @JsonIgnoreProperties("company")
    private List<Designation> designations;
    @JsonIgnoreProperties("company")
    private List<CompanyRole> roles;
    @JsonIgnoreProperties("company")
    private List<CompanyGroup> groups;
}
