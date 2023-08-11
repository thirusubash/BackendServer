package com.gksvp.web.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Embeddable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {

    private String streetAddress;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    // Additional fields for customization
    private String region; // For regions or provinces
    private String district; // For districts or localities
    private String suburb; // For suburbs or neighborhoods
    private String zipCode; // For international postal codes

    // Constructors, getters, and setters

    // Other methods if needed
}
