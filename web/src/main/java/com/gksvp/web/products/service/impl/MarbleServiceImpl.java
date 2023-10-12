package com.gksvp.web.products.service.impl;

import com.gksvp.web.products.dto.MarbleDTO;
import com.gksvp.web.products.entity.Marble;
import com.gksvp.web.products.repository.MarbleRepository;
import com.gksvp.web.products.service.MarbleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarbleServiceImpl implements MarbleService {

    private final MarbleRepository marbleRepository;

    private ModelMapper modelMapper;
    @Autowired
    public MarbleServiceImpl(MarbleRepository marbleRepository, ModelMapper modelMapper) {
        this.marbleRepository = marbleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Marble> findAll() {
        return marbleRepository.findAll();
    }

    @Override
    public Optional<Marble> findById(Long id) {
        return marbleRepository.findById(id);
    }

    @Override
    public Marble save(Marble marble) {
        return marbleRepository.save(marble);
    }

    @Override
    public void delete(Long id) {
        marbleRepository.deleteById(id);
    }

    @Override
    public Optional<Marble> findByType(String type) {
        // Assuming you have a method in your repository called findByType
        return marbleRepository.findByType(type);
    }

    @Override
    public Page<Marble> getCompanyMarbles(Long companyId, String searchTerm, Pageable pageable) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return marbleRepository.findByCompanyId(companyId, pageable);
        } else {
            return marbleRepository.findByCompanyIdAndNameContainingIgnoreCase(companyId, searchTerm, pageable);
        }
    }
    @Override
    public Page<MarbleDTO> fetchActiveProduct(Pageable pageable, String searchKeyword) {

        // Handle null searchKeyword
        if (searchKeyword == null) {
            searchKeyword = "";
        }

        // Fetching the Page of Marbles
        Page<Marble>  marblePage= marbleRepository.findByIsVisibleAndNameContainingIgnoreCaseIfNotNull(true, searchKeyword, pageable);

        // Transforming each Marble entity to MarbleDTO
        return marblePage.map(marble -> modelMapper.map(marble, MarbleDTO.class));
    }



}
