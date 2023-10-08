package com.gksvp.web.products.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "hollow_bricks")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class HollowBricks extends Product {

    private Double length;
    private Double width;
    private Double height;
    private String grade;

    // Additional attributes
    private Double wallThickness;
    private Double hollowRatio;
    private Double compressionStrength;
    private Double weight;
    private Double fireResistanceDuration;
    private Double insulationRating;
    private String manufacturingProcess;
    private String usage;
    private String materialComposition;



}
