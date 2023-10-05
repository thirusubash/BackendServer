package com.gksvp.web.company.repository;

import com.gksvp.web.company.entity.CompanyGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CompanyGroupRepository extends JpaRepository<CompanyGroup, Long> {
    List<CompanyGroup> findByCompany_Id(Long companyId);

    boolean existsByIdAndCompany_Id(Long id, Long companyId);

    Optional<CompanyGroup> findByIdAndCompany_Id(Long id, Long companyId);

    List<CompanyGroup> findByCompany_IdAndNameContaining(Long companyId, String keyword);

    Page<CompanyGroup> findByCompany_IdAndNameContaining(Long companyId, String searchTerm, Pageable pageable);

    Page<CompanyGroup> findByCompany_Id(Long companyId, Pageable pageable);

    CompanyGroup findByName(String admin);
}
