package com.gksvp.web.products.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "marble_products")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Marble extends Product {

    @Column(name = "marble_type")
    private String type;

    @Column(name = "finish")
    private String finish;

    @Column(name = "origin")
    private String origin;

    @Column(name = "pattern")
    private String pattern;

    @Column(name = "thickness")
    private Double thickness;

    @Column(name = "application")
    private String application;

    @Column(name = "durability")
    private String durability;

    @Column(name = "porosity")
    private String porosity;

    @Column(name = "manufacturing_date")
    private LocalDateTime manufacturingDate;

    @Column(name = "polishing_date")
    private LocalDateTime polishingDate;

    @Column(name = "polishing_material")
    private String polishingMaterial;

    @Column(name = "natural_material")
    private String naturalMaterial;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    // Other methods if needed.
}
