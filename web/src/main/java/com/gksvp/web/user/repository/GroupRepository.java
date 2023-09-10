package com.gksvp.web.user.repository;

import com.gksvp.web.user.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Group findByName(String string);

}
