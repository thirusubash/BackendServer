package com.gksvp.web.company.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BankDetailsDTO {

    private Long id;
    private String accountType;
    private String accountNumber;
    private String bankName;
    private String ifsc;
    private boolean primaryAccount;
    private String status;
    private String upiId;
    private String qrCodeUrl;
    private Boolean active;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Long companyId;  // Represents the related company's ID

    // Optionally, if you want the company name or any other details, you can include them:
    // private String companyName;

    // Constructor, getters, setters, etc. can be generated using Lombok or manually.

}
