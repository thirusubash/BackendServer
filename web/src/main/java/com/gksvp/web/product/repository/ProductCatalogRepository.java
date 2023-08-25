package com.gksvp.web.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gksvp.web.product.entity.ProductCatalog;

public interface ProductCatalogRepository extends JpaRepository<ProductCatalog, Long> {
}
