package com.gksvp.web.products.service;

import com.gksvp.web.products.entity.ProductCatalog;
import com.gksvp.web.products.repository.ProductCatalogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCatalogService {

    private final ProductCatalogRepository productCatalogRepository;

    public ProductCatalogService(ProductCatalogRepository productCatalogRepository) {
        this.productCatalogRepository = productCatalogRepository;
    }

    public List<ProductCatalog> getAllProductCatalogs() {
        return productCatalogRepository.findAll();
    }

    public Optional<ProductCatalog> getProductCatalogById(Long id) {
        return productCatalogRepository.findById(id);
    }

    public ProductCatalog createProductCatalog(ProductCatalog productCatalog) {
        return productCatalogRepository.save(productCatalog);
    }

    public ProductCatalog updateProductCatalog(Long id, ProductCatalog updatedCatalog) {
        if (productCatalogRepository.existsById(id)) {
            updatedCatalog.setId(id);
            return productCatalogRepository.save(updatedCatalog);
        }
        return null; // Handle the case where the catalog with the given id doesn't exist
    }

    public void deleteProductCatalog(Long id) {
        productCatalogRepository.deleteById(id);
    }
}
