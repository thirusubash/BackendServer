package com.gksvp.web.products.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.EntityListeners;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)  // Required for auditing
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Granite extends Product {

    @Column(name = "granite_type")
    private String graniteType;  // e.g., Absolute Black, Alaska White, etc.

    @Column(name = "origin_country")
    private String originCountry; // e.g., India, Brazil, etc.

    @Column(name = "thickness")
    private Double thickness; // in millimeters (mm) or inches

    @Column(name = "pattern")
    private String pattern; // e.g., Veined, Speckled, etc.

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    // ... potentially more granite-specific attributes ...

}
