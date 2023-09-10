package com.gksvp.web.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class AddressDto {

    private Long id;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    @JsonIgnoreProperties("address")
    private UserDto user;
}
