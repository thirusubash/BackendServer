package com.gksvp.web.products.entity;


import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class CrushedStone extends Product {

    // Specific Fields for CrushedStone
    private String type; // For example: #57, #411, limestone, marble chips, etc.
    private Boolean isPolished; // Whether the stone is polished or not.

    // Constructor, getters, setters and other methods can be added here
}
