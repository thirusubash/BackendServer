package com.gksvp.web.company.repository;

import com.gksvp.web.company.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    // Define custom query methods here if needed
}
