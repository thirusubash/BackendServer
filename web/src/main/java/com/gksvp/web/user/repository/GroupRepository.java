package com.gksvp.web.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gksvp.web.user.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Group findByName(String string);
    // You can add custom query methods here if needed
}
