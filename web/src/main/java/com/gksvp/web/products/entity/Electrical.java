package com.gksvp.web.products.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
public class Electrical extends Product {

    @Column(name = "voltage")
    private String voltage;  // Voltage rating, e.g., 110V, 220V, etc.

    @Column(name = "wattage")
    private Integer wattage;  // Power consumption or output, e.g., 60W, 100W, etc.

    @Column(name = "safety_certification")
    private String safetyCertification;  // Certifications like CE, UL, etc.

    @Column(name = "product_type")
    private String productType;  // Type of electrical product, e.g., lightbulb, appliance, etc.

    // You can also add additional properties specific to electrical products.


    // Add parameterized constructor, getters, setters, and other methods as needed
}
