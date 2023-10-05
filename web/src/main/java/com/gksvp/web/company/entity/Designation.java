package com.gksvp.web.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Designation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Designation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Boolean Status=true;
    private Boolean defaultDesignation=false;
    private Boolean defaultForUser=false;

    @ManyToOne
    @JoinColumn(name = "companyId")
    @JsonIgnoreProperties({"company","companyAddresses","products","employees","bankAccounts","suppliers","locations","groups","roles","designations","designation","plants"})
    private Company company;

    public Designation(String name, String description,Boolean defaultDesignation) {
        this.name = name;
        this.description = description;
        this.defaultDesignation=defaultDesignation;
    }
}

