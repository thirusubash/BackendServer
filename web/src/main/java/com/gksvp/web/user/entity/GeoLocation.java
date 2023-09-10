package com.gksvp.web.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class GeoLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Set default values for latitude and longitude
    @Column(columnDefinition = "DOUBLE DEFAULT 0.0")
    private double latitude;

    @Column(columnDefinition = "DOUBLE DEFAULT 0.0")
    private double longitude;

    private String ipAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("locations")
    private User user;
}
