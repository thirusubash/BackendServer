package com.gksvp.web.user.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserKYCInfoDto {
    private Long id;
    private String documentType;
    private String documentNumber;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String issueAuthority;
    private String reviewer;
    private boolean status;
}

