package com.gksvp.web.company.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyAdddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Multilingual attributes for internationalization
    @Column(columnDefinition = "TEXT")  // Use TEXT instead of jsonb
    private String localizedAddress;

    // Standard address attributes
    private String addressLine1;  // First line of the address
    private String addressLine2;  // Second line of the address (if needed)
    private String city;           // City or locality
    private String stateProvince;  // State or province
    private String postalCode;     // Postal code or ZIP code
    private String country;        // Country

    // Additional attributes (optional):
    private String county;         // County or region
    private String district;       // District or area
    private String neighborhood;   // Neighborhood
    private String building;       // Building or complex name
    private String floor;          // Floor or level within a building
    private String unit;           // Unit or apartment number
    private String landmark;       // Nearby landmark

    @ManyToOne
    private Company company;
}
