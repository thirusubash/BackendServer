package com.gksvp.web.products.repository;


import com.gksvp.web.products.entity.Marble;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarbleRepository extends JpaRepository<Marble, Long> {
    Optional<Marble> findByType(String type);

    Page<Marble> findByCompanyId(Long companyId, Pageable pageable);

    Page<Marble> findByCompanyIdAndNameContainingIgnoreCase(Long companyId, String searchTerm, Pageable pageable);
}

