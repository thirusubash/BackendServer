package com.gksvp.web.products.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gksvp.web.media.entity.BlobImage;
import com.gksvp.web.media.service.BlobImageService;
import com.gksvp.web.products.entity.ProductCatalog;
import com.gksvp.web.products.service.ProductCatalogService;
import io.jsonwebtoken.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product-catalogs")
@CrossOrigin(origins = "https://localhost:8080", allowCredentials = "true")
public class ProductCatalogController {

    private final ProductCatalogService productCatalogService;
    private final BlobImageService blobImageService;

    public ProductCatalogController(ProductCatalogService productCatalogService, BlobImageService blobImageService) {
        this.productCatalogService = productCatalogService;
        this.blobImageService = blobImageService;
    }

    @PostMapping
    public ResponseEntity<ProductCatalog> createProductCatalog(
            @RequestParam("newCatalog") String newCatalogJson,
            @RequestParam("imageFiles") List<MultipartFile> imageFiles) {
        try {
            if (newCatalogJson == null || newCatalogJson.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            // Deserialize newCatalogJson into ProductCatalog object
            ObjectMapper objectMapper = new ObjectMapper();
            ProductCatalog newCatalog = objectMapper.readValue(newCatalogJson, ProductCatalog.class);

            if (newCatalog == null) {
                return ResponseEntity.badRequest().build();
            }

            List<UUID> imageUUIDs = new ArrayList<>();

            // Store the uploaded image files and get their UUIDs
            for (MultipartFile file : imageFiles) {
                if (file == null) {
                    imageUUIDs.forEach(blobImageService::deleteImage);
                    return ResponseEntity.badRequest().build();
                }

                BlobImage uploadedImage = blobImageService.storeImage(file, "Image for Product Catalog");
                if (uploadedImage == null) {
                    imageUUIDs.forEach(blobImageService::deleteImage);
                    return ResponseEntity.badRequest().build();
                }
                imageUUIDs.add(uploadedImage.getId());
            }

            // Set the generated UUIDs in the ProductCatalog entity
            newCatalog.setImagesURL(imageUUIDs.stream().map(UUID::toString).collect(Collectors.toList()));

            ProductCatalog createdCatalog = productCatalogService.createProductCatalog(newCatalog);
            if (createdCatalog != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(createdCatalog);
            } else {
                imageUUIDs.forEach(blobImageService::deleteImage);
                return ResponseEntity.badRequest().build();
            }
        } catch (JsonProcessingException | IOException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductCatalog>> getAllProductCatalogs() {
        List<ProductCatalog> catalogs = productCatalogService.getAllProductCatalogs();
        return ResponseEntity.ok(catalogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCatalog> getProductCatalogById(@PathVariable Long id) {
        Optional<ProductCatalog> catalog = productCatalogService.getProductCatalogById(id);
        return catalog.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCatalog> updateProductCatalog(@PathVariable Long id,
            @RequestBody ProductCatalog updatedCatalog) {
        ProductCatalog updated = productCatalogService.updateProductCatalog(id, updatedCatalog);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductCatalog(@PathVariable Long id) {
        productCatalogService.deleteProductCatalog(id);
        return ResponseEntity.noContent().build();
    }
}
