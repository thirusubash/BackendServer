package com.gksvp.web.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gksvp.web.user.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    // Built-in methods provided by JpaRepository

    // Custom query method
    List<Role> findByDescription(String description);

    Role findByName(String roleName);
}
