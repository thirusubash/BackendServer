package com.gksvp.web.user.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String mobileNo;
    private Date dateOfBirth;

}
