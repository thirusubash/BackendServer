package com.gksvp.web.company.dto;


import lombok.Data;

@Data
public class CompanyRoleDTO {

    private Long id;
    private String name;
    private String description;
    private Long companyId;

    // Notice that direct entity relationships are represented by IDs in the DTO.

}

