package com.gksvp.web.products.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "msand")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Aggregates extends Product {

    private Double grainSize;  // Size of the sand grains
    private String type;       // Type of M-sand (e.g., Plastering, Concrete, Brick/Blockwork, etc.)
    private Boolean isWashed;  // Specifies if the sand is washed or not
    private String source;     // Source or origin of the M-sand (e.g., riverbed, crushed rock, etc.)

    // You can add more specific attributes for Aggregates here

}
