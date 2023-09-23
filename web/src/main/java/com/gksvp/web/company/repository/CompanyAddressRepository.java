package com.gksvp.web.company.repository;

import com.gksvp.web.company.entity.CompanyAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyAddressRepository extends JpaRepository<CompanyAddress, Long> {
    // Custom queries can be added here if needed
}
