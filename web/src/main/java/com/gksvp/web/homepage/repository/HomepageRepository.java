package com.gksvp.web.homepage.repository;

import com.gksvp.web.homepage.entity.Homepage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HomepageRepository extends JpaRepository<Homepage, Integer>, JpaSpecificationExecutor<Homepage> {

    // Using @Query to explicitly define the search behavior.
    @Query("SELECT h FROM Homepage h WHERE " +
            "(LOWER(h.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(h.detailedDescription) LIKE LOWER(CONCAT('%', :searchTerm1, '%'))) AND " +
            "h.secondaryButtonRedirectUrl IS NOT NULL AND " +
            "h.primaryButtonTitle IS NOT NULL")
    Page<Homepage> findByComplexSearch(@Param("searchTerm") String searchTerm,
                                       @Param("searchTerm1") String searchTerm1,
                                       Pageable pageable);


}
