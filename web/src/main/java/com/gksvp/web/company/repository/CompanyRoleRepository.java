package com.gksvp.web.company.repository;

import com.gksvp.web.company.entity.CompanyRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CompanyRoleRepository extends JpaRepository<CompanyRole, Long> {
    List<CompanyRole> findByCompany_Id(Long companyId);

    boolean existsByIdAndCompany_Id(Long id, Long companyId);

    Optional<CompanyRole> findByIdAndCompany_Id(Long id, Long companyId);  // Use Optional

    CompanyRole findByName(String name);  // Generic name

    List<CompanyRole> findByCompany_IdAndNameContaining(Long companyId, String keyword);
    Page<CompanyRole> findByCompany_IdAndNameContaining(Long companyId,Pageable pageable, String keyword);

    Page<CompanyRole> findByCompany_Id(Long companyId, Pageable pageable);

    Page<CompanyRole> findByCompanyIdAndNameContaining(Long companyId,  Pageable pageable , String searchTerm);
}

