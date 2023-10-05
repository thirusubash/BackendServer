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
public class Electronic extends Product {

    @Column(name = "manufacturer")
    private String manufacturer;  // Manufacturer of the electronic product, e.g., Apple, Samsung, etc.

    @Column(name = "model_number")
    private String modelNumber;  // Model number or name of the electronic product.

    @Column(name = "product_type")
    private String productType;  // Type of electronic, e.g., Smartphone, Laptop, TV, etc.

    @Column(name = "warranty_period")
    private String warrantyPeriod;  // Warranty period like '12 months', '24 months', etc.

    @Column(name = "serial_number")
    private String serialNumber;  // Unique serial number of the product.



    // You can add a parameterized constructor, getters, setters, and other methods as needed.
}
