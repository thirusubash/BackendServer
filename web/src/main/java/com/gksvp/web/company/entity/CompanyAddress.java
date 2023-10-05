package com.gksvp.web.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CompanyAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Multilingual attributes for internationalization
    @Column(columnDefinition = "TEXT")  // Use TEXT instead of jsonb
    private String localizedAddress;

    // Standard address attributes
    @Column(nullable = false)
    @NotEmpty
    private String addressLine1;  // First line of the address
    private String addressLine2;  // Second line of the address (if needed)
    private String city;           // City or locality
    @Column(nullable = false)
    @NotEmpty
    private String stateProvince;  // State or province
    @Column(nullable = false)
    @NotEmpty
    private String postalCode;     // Postal code or ZIP code
    @Column(nullable = false)
    @NotEmpty
    private String country;        // Country

    // Additional attributes (optional):
    private String county;         // County or region
    @Column(nullable = false)
    @NotEmpty
    private String district;       // District or area
    private String neighborhood;   // Neighborhood
    @Column(nullable = false)
    @NotEmpty
    private String building;       // Building or complex name
    private String floor;          // Floor or level within a building
    private String unit;           // Unit or apartment number
    private String landmark;
    private boolean status=true;// Nearby landmark

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime lastModifiedDate;

    @ManyToOne
    @JsonIgnoreProperties({"companyAddresses","products","plants","employees","bankAccounts","suppliers","locations"})
    private Company company;
}
