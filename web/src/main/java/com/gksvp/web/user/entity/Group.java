package com.gksvp.web.user.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "auth_group")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
    private String description;


    @ManyToMany(mappedBy = "groups")
    @JsonIgnoreProperties({"groups","addresses","kycInfoList","locations"})
    private List<User> users;


    public void addUser(User createdUser) {
    }
}
