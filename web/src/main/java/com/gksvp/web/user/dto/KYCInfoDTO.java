package com.gksvp.web.user.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class KYCInfoDTO {

    private String documentType;
    private String documentNumber;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String issueAuthority;
    // Other KYC related attributes

    // Constructors, getters, setters, etc.
}
