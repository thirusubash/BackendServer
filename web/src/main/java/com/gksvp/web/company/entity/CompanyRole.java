package com.gksvp.web.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Boolean status = true;
    private Boolean defaultRole = true;
    private Boolean defaultForUser = false;
    @ManyToOne
    @JoinColumn(name = "companyId")
    @JsonIgnoreProperties({"companyAddresses","products","employees","bankAccounts","suppliers","locations","groups","roles","designations","company","plants"})
    private Company company;

    public CompanyRole(String name, String description,Boolean defaultRole) {
        this.name = name;
        this.description = description;
    }

}

