package com.gksvp.web.util.geo;

import jakarta.annotation.Nullable;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Nullable
public class GeoLocation {

    private double latitude;
    private double longitude;

    // Constructors, getters, and setters

    // Other methods if needed
}
