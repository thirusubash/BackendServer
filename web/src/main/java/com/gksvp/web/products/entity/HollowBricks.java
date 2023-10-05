package com.gksvp.web.products.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "hollow_bricks")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class HollowBricks extends Product {

    private Double length; // Length of the hollow brick
    private Double width;  // Width of the hollow brick
    private Double height; // Height of the hollow brick
    private String grade;  // Grade of the brick (e.g., Grade A, Grade B, etc.)

    // You can add more specific attributes for HollowBricks here

}
