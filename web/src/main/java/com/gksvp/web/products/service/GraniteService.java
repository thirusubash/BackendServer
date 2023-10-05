package com.gksvp.web.products.service;


import com.gksvp.web.products.entity.Granite;
import com.gksvp.web.products.repository.GraniteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GraniteService {

    @Autowired
    private GraniteRepository graniteRepository;

    public List<Granite> getAllGranites() {
        return graniteRepository.findAll();
    }

    public Optional<Granite> getGraniteById(Long id) {
        return graniteRepository.findById(id);
    }

    public Granite saveOrUpdateGranite(Granite granite) {
        return graniteRepository.save(granite);
    }

    public void deleteGranite(Long id) {
        graniteRepository.deleteById(id);
    }
}
