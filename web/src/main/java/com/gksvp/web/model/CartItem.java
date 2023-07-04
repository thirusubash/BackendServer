package com.gksvp.web.model;

import jakarta.persistence.EmbeddedId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @EmbeddedId
    private Long id;
    private Item item;
    private int quantity;
}
