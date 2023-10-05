package com.gksvp.web.products.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
public class Plumbing extends Product {

    @Column(name = "type")
    private String type;  // e.g., Faucet, Pipe, Valve, etc.

    @Column(name = "material")
    private String material;  // e.g., PVC, Brass, Copper, etc.

    @Column(name = "dimensions")
    private String dimensions;  // e.g., '2-inch diameter' or '50mm x 100mm'

    @Column(name = "pressure_rating")
    private String pressureRating;  // e.g., '200 PSI'

    @Column(name = "brand")
    private String brand;  // Manufacturer or brand name, e.g., Delta, Kohler, etc.

    @Column(name = "installation_type")
    private String installationType;  // e.g., Wall-mounted, Deck-mounted, etc.

    public Plumbing() {
        // Default constructor
    }

    // You can add a parameterized constructor, getters, setters, and other methods as needed.
}
