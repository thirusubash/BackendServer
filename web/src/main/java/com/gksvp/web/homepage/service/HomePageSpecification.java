package com.gksvp.web.homepage.service;

import org.springframework.data.jpa.domain.Specification;
import com.gksvp.web.homepage.entity.Homepage; // Import the Homepage entity as well

public class HomePageSpecification {

    public static Specification<Homepage> isVisible() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("isVisible"));
    }

}
