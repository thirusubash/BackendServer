package com.gksvp.web.repository.user;

import com.gksvp.web.model.user.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Group findByName(String string);
    // You can add custom query methods here if needed
}
