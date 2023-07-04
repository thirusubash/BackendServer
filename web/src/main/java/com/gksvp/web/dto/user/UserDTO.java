package com.gksvp.web.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 5, message = "Username must have at least 5 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private String countrycode;
    private String mobileNo;

    @NotBlank(message = "Password is required")
    @Size(min = 5, message = "Password must have at least 5 characters")
    private String password;

    @NotBlank(message = "First name is required")
    private String firstname;

    @NotBlank(message = "Last name is required")
    private String lastname;

    private String PAN;
    private String adhaar;
    private String gstin;
    private Integer pincode;
    private Boolean active;
    private String houseNo;
    private String building;
    private String street;
    private String city;
    private String state;
    private String landmark;
    private String location;

    // Constructors

    // Other methods as required

    // Getters and setters
}
