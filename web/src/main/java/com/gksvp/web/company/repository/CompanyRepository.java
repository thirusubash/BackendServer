package com.gksvp.web.company.repository;


import com.gksvp.web.company.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Page<Company> findByCompanyNameContainingIgnoreCase(String keyword, Pageable pageable);
    // Define custom query methods here if needed
}
