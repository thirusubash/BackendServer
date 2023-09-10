package com.gksvp.web.user.dto;
import lombok.Data;

@Data
public class GeoLocationDto {

    private Long id;
    private double latitude;
    private double longitude;
    private String ipAddress;
}

