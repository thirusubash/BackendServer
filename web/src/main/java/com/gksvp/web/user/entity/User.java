package com.gksvp.web.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gksvp.web.util.geo.GeoLocation;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;

    @Column(name = "country_code", nullable = false)
    private String countryCode;

    @Column(name = "mobile_no", unique = true, nullable = false)
    private String mobileNo;

    @Column(name = "alternate_mobile_no")
    private String alternateMobileNo;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "active")
    private Boolean active;

    @Embedded
    private Address address;

    @Embedded
    private GeoLocation location;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    private Boolean mobileVerified;
    private Boolean emailVerified;

    @JsonIgnoreProperties({ "users" })
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @JsonIgnoreProperties({ "users" })
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_groups", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kyc_info_id", referencedColumnName = "kyc_info_id")
    private UserKYCInfo kycInfo;

    public void addRole(Role role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.add(role);
    }

    public void removeRole(Role role) {
        if (roles != null) {
            roles.remove(role);
        }
    }

    public void addGroup(Group group) {
        if (groups == null) {
            groups = new HashSet<>();
        }
        groups.add(group);
    }

    public void removeGroup(Group group) {
        if (groups != null) {
            groups.remove(group);
        }
    }

    public GeoLocation getLocation() {
        return location;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }

}
