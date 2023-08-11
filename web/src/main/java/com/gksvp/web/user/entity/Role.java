package com.gksvp.web.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@EqualsAndHashCode(exclude = "users")
@Entity
@Table(name = "roles")
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role() {
        this.users = new ArrayList<>();
    }

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
        this.users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
        user.getRoles().add(this);
    }

    public void removeUser(User user) {
        users.remove(user);
        user.getRoles().remove(this);
    }

}
