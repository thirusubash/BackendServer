package com.gksvp.web.company.dto;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DesignationDTO {
    private Long id;
    private String name;
    private String description;
    private Boolean Status;
    private Boolean defaultDesignation;
    private Boolean defaultForUser;
}
