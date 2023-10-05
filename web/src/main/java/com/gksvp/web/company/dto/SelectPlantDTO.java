package com.gksvp.web.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SelectPlantDTO {

    private Long id;
    private String name;

    public static class SupplierDTO {
    }
}
