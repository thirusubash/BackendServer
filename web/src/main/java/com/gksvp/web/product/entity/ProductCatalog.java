package com.gksvp.web.product.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class ProductCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String catalogTitle;
    private String subTitle;
    private String description;
    private String message;
    private String route;

    @ElementCollection
    private List<String> imagesURL;

    private String category;
}
