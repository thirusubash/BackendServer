package com.gksvp.web.company.repository;

import com.gksvp.web.company.entity.CompanyAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyAddressRepository extends JpaRepository<CompanyAddress, Long> {

    // Search based on companyId and keyword match on address
    Page<CompanyAddress> findByCompanyIdAndAddressLine1Containing(Long companyId, String keyword, Pageable pageable);

    // Custom query to match the keyword with addressLine1, postalCode, country, or district based on companyId
    @Query("SELECT ca FROM CompanyAddress ca WHERE ca.company.id = :companyId AND (ca.addressLine1 LIKE %:keyword% OR ca.postalCode LIKE %:keyword% OR ca.country LIKE %:keyword% OR ca.district LIKE %:keyword%)")
    Page<CompanyAddress> findByCompanyIdAndKeyword(
            @Param("companyId") Long companyId,
            @Param("keyword") String keyword,
            Pageable pageable);

    // Get all company addresses based on companyId
    Page<CompanyAddress> findByCompanyId(Long companyId, Pageable pageable);
}
