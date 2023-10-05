package com.gksvp.web.products.repository;


import com.gksvp.web.products.entity.Granite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GraniteRepository extends JpaRepository<Granite, Long> {
    // You can add custom queries if needed
}
