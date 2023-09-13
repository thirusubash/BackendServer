package com.gksvp.web.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class UserDto {

    private Long id;
    private String userName;
    private String email;
    private String countryCode;
    private String mobileNo;
    private String alternateMobileNo;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;
//    private Boolean mobileVerified;
//    private Boolean emailVerified;
    private Boolean active;
    private String url;

    @JsonIgnoreProperties("user")
    private List<AddressDto> addresses;

    @JsonIgnoreProperties("user")
    private List<UserKYCInfoDto> kycInfoList;

    @JsonIgnoreProperties("user")
    private List<GeoLocationDto> locations;

    @JsonIgnoreProperties("users")
    private Set<RoleDto> roles;

    @JsonIgnoreProperties("users")
    private Set<GroupDto> groups;
}
