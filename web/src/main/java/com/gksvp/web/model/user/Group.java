package com.gksvp.web.model.user;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "auth_group")
@Data
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Other attributes
    private String name;
    private String description;

    // Define the association with users
    @JsonBackReference
    @ManyToMany
    @JoinTable(name = "auth_group_users", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    // Constructors, getters, and setters

    public Group() {
        this.users = new ArrayList<>();
    }

    // Add a user to the group
    public void addUser(User user) {
        users.add(user);
        user.getGroups().add(this);
    }

    // Remove a user from the group
    public void removeUser(User user) {
        users.remove(user);
        user.getGroups().remove(this);
    }

    // Getters and setters

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    // Other methods
}
