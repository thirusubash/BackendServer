package com.gksvp.web.company.repository;



import com.gksvp.web.company.entity.CompanyLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyLocationRepository extends JpaRepository<CompanyLocation, Long> {
    // Here you can add custom queries if needed
}
