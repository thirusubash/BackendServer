package com.gksvp.web.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SelectCompanyDTO {
    private Long id;
    private String companyName;
    private String companyId;
}
