package com.gksvp.web.products.repository;

import com.gksvp.web.products.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> hasColor(String color) {
        return (product, cq, cb) -> cb.equal(product.get("color"), color);
    }

    public static Specification<Product> hasPattern(String pattern) {
        return (product, cq, cb) -> cb.equal(product.get("pattern"), pattern);
    }


    public static Specification<Product> hasBrand(String brand) {
        return (product, cq, cb) -> cb.equal(product.get("pattern"), brand);
    }
}
