package com.gksvp.web.company.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class BasicCompanyDTO {
    private Long id;
    private String companyId;
    private String companyName;
    private String businessType;
    private String registrationNumber;
    private String registrationAuthority;
    private LocalDateTime dateOfIncorporation;
    private String businessActivities;
    private String industryClassification;
    private String financialStatements;
    private String contactNumber;
    private String email;
    private String llpNumber;
    private String trademarkAndIP;
    private String insuranceInformation;
    private Boolean status;
}
