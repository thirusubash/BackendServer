package com.gksvp.web.company.repository;



import com.gksvp.web.company.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    // Define custom query methods here if needed
}

