package com.gksvp.web.products.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gksvp.web.company.entity.Company;
import com.gksvp.web.company.entity.Plant;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Product {

    // Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_gen")
    @SequenceGenerator(name = "product_gen", sequenceName = "product_seq", allocationSize = 1)
    private Long id;

    // Fields
    private String name;
    private Double price;
    private String description;
    private String sku;
    private String brand;
    private String category;
    private Double weight;
    private String dimensions;
    private String color;
    private String material;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "manufacturing_date_time")
    private LocalDateTime manufacturingDateTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;
    private Integer stockQuantity;
    private Double rating;

    @ElementCollection
    @CollectionTable(name = "product_image_urls", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private Set<String> imageUrls = new LinkedHashSet<>();
    private String tags;
    private Boolean isActive;
    private Boolean isVisibleToUsers = true;
    @Column(unique = true, updatable = false, nullable = false)
    private UUID uuid;

    // Entity Relationships
    @ManyToOne()
    @JoinColumn(name = "company_id")
    @JsonIgnoreProperties({"companyAddresses", "products", "employees", "bankAccounts", "suppliers", "locations", "roles", "groups", "designations", "plants", "plant", "company"})
    private Company company;
    @ManyToOne()
    @JoinColumn(name = "plant_id")
    @JsonIgnoreProperties({"companyAddresses", "products", "employees", "bankAccounts", "suppliers", "locations", "roles", "groups", "designations", "plants", "plant", "company"})
    private Plant plant;

    // Auditing fields
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;
    @CreatedDate
    @Column(updatable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    @LastModifiedBy
    private String lastModifiedBy;
    @LastModifiedDate
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated;

    // Lifecycle Callbacks
    @PrePersist
    public void generateUUID() {
        System.out.println("Generating UUID...");
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
        System.out.println("Generated UUID: " + this.uuid);
    }
    public void addImageUrl(String imageUrl) {
        imageUrls.add(imageUrl);
    }

    public boolean removeImageUrl(String imageUrl) {
        return imageUrls.remove(imageUrl);
    }


}
