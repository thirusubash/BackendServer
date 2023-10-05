package com.gksvp.web.products.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasicProductDTO {
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
    private String imageURL;
    private Boolean isActive;
    private String tags;
}
