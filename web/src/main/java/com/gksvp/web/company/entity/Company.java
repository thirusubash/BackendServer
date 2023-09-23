package com.gksvp.web.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private Boolean status;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime lastModifiedDate;


    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("company")
    private List<CompanyAddress> companyAddresses;

    @OneToMany( cascade = CascadeType.ALL)
    @JsonIgnoreProperties("company")
    private List<Plant> plants;

    @OneToMany(mappedBy = "company", cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties("company")
    private List<Employee> employees;


    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("companies")
    private List<Supplier> suppliers;


    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("company")
    private List<CompanyLocation> locations;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("company")
    private Set<BankDetails> bankAccounts = new HashSet<>();

}

