package com.gksvp.web.company.dto;

import lombok.Data;

@Data
public class CompanyGroupDTO {

    private Long id;
    private String name;
    private String description;
    private Boolean status;
    private Boolean defaultGroup;
    private Boolean defaultForUser;

}
