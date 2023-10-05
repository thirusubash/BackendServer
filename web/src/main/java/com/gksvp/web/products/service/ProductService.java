package com.gksvp.web.products.service;

import com.gksvp.web.products.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface ProductService {

    // Basic CRUD Operations
    Product saveProduct(Product product);

    Optional<Product> getProductById(Long id);

    void deleteProduct(Long id);

    // Retrieval Operations
    Page<Product> getAllProducts(Pageable pageable);

    Page<Product> getAllProductsByCompany(Long companyId, Pageable pageable);

    // Search Operations
    Page<Product> searchProducts(String color, String pattern, String brand, Pageable pageable);

    Page<Product> searchProductsByCompany(Long companyId, String keyword, Pageable pageable);
    Product searchProductsByUUID(UUID uuid);
    // Business Logic
    boolean doesProductBelongToCompany(Long productId, Long companyId);

    Product updateProductVisibility(Long companyId, Long productId, Boolean status);
    Product updateStatus(Long companyId, Long id, Boolean status);

    Product addImage(Long id, String imageUrl);
    Product removeImage(Long id, String imageUrl);

}
