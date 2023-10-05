package com.gksvp.web.company.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SupplierDTO {

    private Long id;
    private String name;
    private String contactPerson;
    private String email;
    private String phoneNumber;
    private String address;
    private String pincode;
    private String website;
    private String paymentTerms;
    private String contractStartDate;
    private String contractEndDate;
    private String qualityRating;
    private String reliabilityRating;
    private String reviews;
    private String bankName;
    private String accountNumber;
    private String ifsc;
    private String taxId;
    private String certifications;
    private Boolean status;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    // If you want to include related entity IDs or DTOs, you can add them here.
    // For instance:
    // private List<Long> companyIds;

    // Notice that direct entity relationships and sensitive fields are excluded from the DTO.

}

