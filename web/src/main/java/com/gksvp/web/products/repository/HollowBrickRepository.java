package com.gksvp.web.products.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gksvp.web.products.entity.HollowBricks;

@Repository
public interface HollowBrickRepository extends JpaRepository<HollowBricks, Long> {


    Page<HollowBricks> findByCompanyId(Long companyId, Pageable pageable);

    Page<HollowBricks> findByCompanyIdAndNameContainingIgnoreCase(Long companyId, String searchTerm, Pageable pageable);



}
