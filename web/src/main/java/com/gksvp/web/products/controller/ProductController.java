package com.gksvp.web.products.controller;

import com.gksvp.web.exception.ProductNotFoundException;
import com.gksvp.web.products.entity.Product;
import com.gksvp.web.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    // --- Basic CRUD for products ---
    // Fetch paginated products
    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable) {
        Page<Product> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(products);
    }


    // Fetch a single product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    // Add a new product
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    // Update an existing product
    @PutMapping("/{companyId}/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @PathVariable Long companyId, @RequestBody Product updatedProduct) {

        if (productService.doesProductBelongToCompany(id, companyId)) {
            updatedProduct.setId(id);
            return ResponseEntity.ok(productService.saveProduct(updatedProduct));

        }return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productService.getProductById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();  // Use noContent for successful DELETE operations without a response body
    }
    @PatchMapping("/status/{companyId}/{id}")
    public ResponseEntity<Product> updateStatus(@PathVariable Long id,
                                                @PathVariable Long companyId,
                                                @RequestParam Boolean status) {
        {

            // Check if the product belongs to the specified company
            if (productService.doesProductBelongToCompany(id, companyId)) {
                Product updatedProduct = productService.updateStatus(companyId, id, status);
                return ResponseEntity.ok(updatedProduct);
            }
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/visibility/{companyId}/{id}")
    public ResponseEntity<Product> updateVisibility(@PathVariable Long id,
                                                    @PathVariable Long companyId,
                                                    @RequestParam Boolean visibility) {

        // Check if the product belongs to the specified company
        if (productService.doesProductBelongToCompany(id, companyId)) {
            Product updatedProduct = productService.updateProductVisibility(companyId, id, visibility);
            return ResponseEntity.ok(updatedProduct);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete a  product by companyId
    @DeleteMapping("/{companyId}/{productId}")
    public ResponseEntity<Void> deleteCompanyProduct(@PathVariable Long companyId, @PathVariable Long productId) {
        // Check if the product exists and belongs to the given company
        if (!productService.doesProductBelongToCompany(productId, companyId)) {
            return ResponseEntity.notFound().build();
        }

        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();  // Use noContent for successful DELETE operations without a response body
    }



    // --- Company-specific endpoints ---
    @GetMapping("/company/{companyId}/search")
    public ResponseEntity<Page<Product>> getCompanyAllProducts(
            @PathVariable Long companyId,
            @RequestParam(required = false) String search,
            Pageable pageable) {

        Page<Product> products;

        if (search != null && !search.isEmpty()) {
            products = productService.searchProductsByCompany(companyId, search, pageable);
        } else {
            products = productService.getAllProductsByCompany(companyId, pageable);
        }
        return ResponseEntity.ok(products);
    }
    @GetMapping("/company/{companyId}")
    public ResponseEntity<Page<Product>> getAllProductsByCompanyID(@PathVariable Long companyId, Pageable pageable ) {
        Page<Product> products = productService.getAllProductsByCompany(companyId, pageable);

        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(products);
    }
    // --- General search endpoint for products ---
    // Search for products based on criteria
    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchProducts(
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String pattern,
            @RequestParam(required = false) String brand,
            Pageable pageable) {
        return ResponseEntity.ok(productService.searchProducts(color, pattern, brand, pageable));
    }

    @GetMapping("/search/{uuid}")
    public ResponseEntity<Product> searchProductByUUID(
            @PathVariable UUID uuid) {

        Product product = productService.searchProductsByUUID(uuid);

        if(product == null) {
            throw new ProductNotFoundException("Product with UUID " + uuid + " not found.");
        }

        return ResponseEntity.ok(product);
    }


    @PatchMapping("/image")
    public ResponseEntity<Product> image(
            @RequestParam long companyId,  // Fixed typo here
            @RequestParam Long id,
            @RequestParam String image) {

        if(productService.doesProductBelongToCompany(id, companyId)) {
            Product product = productService.addImage(id, image);
            return ResponseEntity.ok(product);
        }
        throw new ProductNotFoundException("Product with ID " + id + " not found.");
    }

    @PatchMapping("/{companyId}/{id}/remove-image/{image}")
    public ResponseEntity<Product> removeImage(@PathVariable Long companyId,
                                               @PathVariable Long id,
                                               @PathVariable String image) {
        if(productService.doesProductBelongToCompany(id, companyId)) {
            Product product = productService.removeImage(id, image);
            return ResponseEntity.ok(product);
        }
        throw new ProductNotFoundException("Product with ID " + id + " not found.");
    }


}
