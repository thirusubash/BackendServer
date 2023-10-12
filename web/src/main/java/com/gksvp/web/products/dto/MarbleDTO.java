package com.gksvp.web.products.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class MarbleDTO {

    private Long id;
    private String name;
    private Double price;
    private String description;
    private String sku;
    private String brand;
    private String category;
    private Double weight;
    private String dimensions;
    private String color;
    private String material;
    private String manufacturingDateTime;
    private String expiryDate;
    private Integer stockQuantity;
    private Double rating;
    private Set<String> imageUrls = new LinkedHashSet<>();
    private String tags;
    private String type;
    private String finish;
    private String origin;
    private String pattern;
    private Double thickness;
    private String application;
    private String durability;
    private String porosity;
    private LocalDateTime manufacturingDate;
    private LocalDateTime polishingDate;
    private String polishingMaterial;
    private String naturalMaterial;
    private String createdBy;
    private LocalDateTime createdDate;

}
