package com.gksvp.web.products.service.impl;

import com.gksvp.web.products.entity.Product;
import com.gksvp.web.products.repository.ProductRepository;
import com.gksvp.web.products.repository.ProductSpecification;
import com.gksvp.web.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> searchProducts(String color, String pattern, String brand, Pageable pageable) {
        Specification<Product> spec = Specification.where(
                ProductSpecification.hasColor(color)
                        .and(ProductSpecification.hasPattern(pattern))
                        .and(ProductSpecification.hasBrand(brand))
        );
        return productRepository.findAll(spec, pageable);
    }

    @Override
    public Page<Product> getAllProductsByCompany(Long companyId, Pageable pageable) {
        return productRepository.findByCompany_Id(companyId, pageable);
    }

    @Override
    public Page<Product> searchProductsByCompany(Long companyId, String keyword, Pageable pageable) {
        return productRepository.findByCompanyIdAndNameContaining(companyId, keyword, pageable);
    }

    @Override
    public Product searchProductsByUUID(UUID uuid) {
        return productRepository.findByUuid(uuid).orElse(null);
    }


    @Override
    public boolean doesProductBelongToCompany(Long productId, Long companyId) {
        Optional<Product> product = getProductById(productId);
        return product.map(value -> value.getCompany().getId().equals(companyId)).orElse(false);
    }

    @Override
    public Product updateProductVisibility(Long companyId, Long productId, Boolean status) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isEmpty()) {
            return null;  // Return null or optionally you could throw a not found exception.
        }

        Product product = productOptional.get();

//        // Check if the product belongs to the given company
//        if(!product.getCompany().getId().equals(companyId)) {
//            return null;  // Or throw a forbidden or not found exception
//        }

        product.setIsVisibleToUsers(status);
        return productRepository.save(product);  // Return the updated product.
    }


    @Override
    public Product updateStatus(Long companyId, Long id, Boolean status) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isEmpty()) {
            return null;  // Return null or optionally you could throw a not found exception.
        }

        Product product = productOptional.get();

//        // Check if the product belongs to the given company
//        if(!product.getCompany().getId().equals(companyId)) {
//            return null;  // Or throw a forbidden or not found exception
//        }

        product.setIsActive(status);
        return productRepository.save(product);
    }

    @Override
    public Product addImage(Long id, String imageUrl) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()) {
            Product product = productOptional.get();
            product.getImageUrls().add(imageUrl);
            return productRepository.save(product);
        }
        // Handle the case where the product doesn't exist, maybe throw an exception or return null
        return null;
    }

    @Override
    public Product removeImage(Long id, String imageUrl) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()) {
            Product product = productOptional.get();
            product.getImageUrls().remove(imageUrl);
            return productRepository.save(product);
        }
        // Handle the case where the product doesn't exist, maybe throw an exception or return null
        return null;
    }


}
