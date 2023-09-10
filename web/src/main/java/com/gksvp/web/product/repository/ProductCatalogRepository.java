package com.gksvp.web.product.repository;

import com.gksvp.web.product.entity.ProductCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCatalogRepository extends JpaRepository<ProductCatalog, Long> {
}
