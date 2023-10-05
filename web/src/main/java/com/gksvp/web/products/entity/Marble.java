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
    private String type;  // e.g., Italian, Granite, etc.

    @Column(name = "finish")
    private String finish;  // e.g., Polished, Honed, etc.

    @Column(name = "origin")
    private String origin;  // e.g., Italy, Spain, India, etc.

    @Column(name = "pattern")
    private String pattern;  // e.g., Veined, Flecked, etc.

    @Column(name = "thickness")
    private Double thickness;  // e.g., 20mm, 30mm, etc.

    @Column(name = "application")
    private String application;  // e.g., Flooring, Countertops, Wall cladding, etc.

    @Column(name = "durability")
    private String durability;  // e.g., High, Medium, Low

    @Column(name = "porosity")
    private String porosity;  // e.g., Porous, Less Porous, Non-porous

    @Column(name = "manufacturing_date")
    private LocalDateTime manufacturingDate;

    @Column(name = "polishing_date")
    private LocalDateTime polishingDate;

    @Column(name = "polishing_material")
    private String polishingMaterial;

    @Column(name = "natural_material")
    private String naturalMaterial;  // e.g., Quartz, Feldspar, etc.

    @Column(name = "orgin")
    private String orgin;

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

    // Constructors, getters, setters, and other methods if needed.
}
