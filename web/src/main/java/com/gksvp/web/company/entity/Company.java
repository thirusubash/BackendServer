package com.gksvp.web.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import  jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyId;
    private String companyName;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("company")
    private List<CompanyAdddress> companyAddresses;

    @OneToMany( cascade = CascadeType.ALL)
    @JsonIgnoreProperties("company")
    private List<Plant> plants;

    @OneToMany(cascade = CascadeType.ALL)
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

