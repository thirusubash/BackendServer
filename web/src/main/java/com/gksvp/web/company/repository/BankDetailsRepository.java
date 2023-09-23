package com.gksvp.web.company.repository;


import com.gksvp.web.company.entity.BankDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankDetailsRepository extends JpaRepository<BankDetails, Long> {

    // You can add custom query methods if needed.
    // Example: Find all bank details for a given company
    List<BankDetails> findByCompany_Id(Long companyId);

    // Example: Find the primary account for a given company
    BankDetails findByCompany_IdAndPrimaryAccountTrue(Long companyId);

    // Additional methods as per your requirements...
}
