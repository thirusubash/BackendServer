package com.gksvp.web.products.repository;



import com.gksvp.web.products.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Page<Product> findByNameContaining(String keyword, Pageable pageable);

    Page<Product> findByCompany_Id(Long companyId, Pageable pageable);


    Optional<Product> findByUuid(UUID uuid);

    Page<Product> findByCompanyIdAndNameContaining(Long companyId, String keyword, Pageable pageable);


}
