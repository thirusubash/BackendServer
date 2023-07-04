package com.gksvp.web.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
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
    private Integer id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "countrycode")
    private String countrycode;

    @Column(name = "mobile_no", unique = true, nullable = false)
    private String mobileNo;

    @Column(name = "Alter_mobile_no")
    private String alternateno;

    @Column(name = "password")
    private String password;

    @Column(name = "name", nullable = false)
    private String firstname;

    @Column(name = "last_name", nullable = false)
    private String lastname;

    @Column(name = "PAN", unique = true, nullable = false)
    private String PAN;

    @Column(name = "adhaar", unique = true, nullable = false)
    private String adhaar;

    @Column(name = "gstin", unique = true, nullable = false)
    private String gstin;

    @Column(name = "pincode", nullable = false)
    private Integer pincode;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "house_no")
    private String houseNo;

    @Column(name = "building")
    private String building;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "reg_loc")
    private String location;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    @JsonIgnoreProperties({ "users" })
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @JsonIgnoreProperties({ "users" })
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_groups", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups;

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
}
