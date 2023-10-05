package com.gksvp.web.company.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SelectSupplierDTO {
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;
}
