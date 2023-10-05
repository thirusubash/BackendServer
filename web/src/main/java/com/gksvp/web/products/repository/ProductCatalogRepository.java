package com.gksvp.web.products.repository;

import com.gksvp.web.products.entity.ProductCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCatalogRepository extends JpaRepository<ProductCatalog, Long> {
}
