package com.gksvp.web.company.repository;

import com.gksvp.web.company.entity.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {
    List<Designation> findByCompany_Id(Long companyId);

    boolean existsByIdAndCompany_Id(Long id, Long companyId);

    Designation findByIdAndCompany_Id(Long id, Long companyId);

    Designation findByName(String ceo);
}
