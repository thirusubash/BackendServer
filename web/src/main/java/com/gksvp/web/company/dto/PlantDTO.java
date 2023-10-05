package com.gksvp.web.company.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PlantDTO {

    private Long id;
    private String name;
    private String location;
    private String capacity;
    private String primaryProduct;
    private String machineryTypes;
    private int operationalHours;
    private String qualityStandards;
    private String safetyStandards;
    private String environmentalImpact;
    private String rawMaterialSource;
    private String email;
    private String contactNumber;
    private String storageCapacity;
    private Boolean status;
    private String wasteDisposalSystem;
    private String polishingAgents;
    private String regulationsCompliance;
    private Date createdDate;
    private Date lastModifiedDate;

    // If you want to include related entity IDs or DTOs, you can include them here.
    // For instance:
    // private Long companyId;
    // private List<Long> employeeIds;
    // private List<Long> productIds;

    // The password field is excluded from the DTO to ensure security.

}
