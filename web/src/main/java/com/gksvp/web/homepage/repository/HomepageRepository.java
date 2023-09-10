
package com.gksvp.web.homepage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gksvp.web.homepage.entity.Homepage;

public interface HomepageRepository extends JpaRepository<Homepage, Integer> {
    // You can add custom query methods here if needed
}
