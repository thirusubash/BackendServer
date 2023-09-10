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
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String streetAddress;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String region;
    private String district;
    private String suburb;
    private String zipCode;
    private Boolean active = false;

    // Many addresses belong to one user, so define the user reference
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"addresses"})// This specifies the foreign key column
    private User user;

    // Define the setUser method to set the user reference
    public void setUser(User user) {
        this.user = user;
    }
}
