package com.gksvp.web.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gksvp.web.model.user.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    // Built-in methods provided by JpaRepository

    // Custom query method
    List<Role> findByDescription(String description);

    Role findByName(String roleName);
}
