package com.gksvp.web.products.service;

import com.gksvp.web.products.dto.MarbleDTO;
import com.gksvp.web.products.entity.Marble;
import io.micrometer.core.instrument.binder.db.MetricsDSLContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MarbleService {

    List<Marble> findAll();

    Optional<Marble> findById(Long id);

    Marble save(Marble marble);

    void delete(Long id);

    Optional<Marble> findByType(String type);

    Page<Marble> getCompanyMarbles(Long companyId, String searchTerm, Pageable pageable);


    Page<MarbleDTO> fetchActiveProduct(Pageable pageable, String searchKeyword);

}
