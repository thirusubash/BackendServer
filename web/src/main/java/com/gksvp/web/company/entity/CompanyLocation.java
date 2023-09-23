package com.gksvp.web.company.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class CompanyLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    private String ipAddress;

    @ManyToOne// This creates a foreign key column in CompanyLocation
    @JsonBackReference
    private Company company;

    @PrePersist
    public void setDefaultValues() {
        if (latitude == 0.0) {
            latitude = 0.0; // Set your default latitude value here
        }

        if (longitude == 0.0) {
            longitude = 0.0; // Set your default longitude value here
        }
    }
}
