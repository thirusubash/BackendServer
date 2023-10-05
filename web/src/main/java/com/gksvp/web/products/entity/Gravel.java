package com.gksvp.web.products.entity;



import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@NoArgsConstructor  // Lombok annotation for a no-argument constructor
@Getter             // Lombok annotation to generate getters
@Setter             // Lombok annotation to generate setters
public class Gravel extends Product {

    private String gravelType;  // Example: Crushed, Pea Gravel, Jersey Shore, etc.
    private boolean isWashed;   // Indicates if the gravel is washed or not

    // Lombok will automatically generate the default constructor, getters, and setters for these fields

}

