package com.gksvp.web.user.dto;

import com.gksvp.web.user.entity.Address;
import com.gksvp.web.util.geo.GeoLocation; // Import the GeoLocation class

import lombok.Data;

@Data
public class UserDTO {

    private String id;
    private String userName;
    private String countryCode;
    private String email;
    private String mobileNo;
    private String alternateMobileNo;
    private String firstName;
    private String lastName;
    private Address address;
    private boolean mobileVerified;
    private boolean emailVerified;
    private Boolean active;
    private GeoLocation location;
    private KYCInfoDTO kycInfoDto;

}
