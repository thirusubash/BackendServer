package com.gksvp.web.user.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gksvp.web.user.entity.Role;

import java.util.Optional;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gksvp.web.user.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
