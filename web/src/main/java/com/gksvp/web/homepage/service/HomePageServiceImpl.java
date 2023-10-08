package com.gksvp.web.homepage.service;

import com.gksvp.web.homepage.dto.HomePageDTO;
import com.gksvp.web.homepage.entity.Homepage;
import com.gksvp.web.homepage.repository.HomepageRepository;
import com.gksvp.web.media.service.BlobImageService;
import io.micrometer.common.util.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.gksvp.web.homepage.service.HomePageSpecification;
@Service
public class HomePageServiceImpl implements HomePageService {

    private final HomepageRepository homepageRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public HomePageServiceImpl(HomepageRepository homepageRepository, BlobImageService blobImageService, ModelMapper modelMapper) {
        this.homepageRepository = homepageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Homepage createHomepage(Homepage homepage) {
        return homepageRepository.save(homepage);
    }

    @Override
    public Homepage getHomepageById(Integer id) {
        return homepageRepository.findById(id).orElse(null);
    }

    @Override
    public Homepage updateHomepage(Integer id, Homepage homepage) {
        if(homepageRepository.existsById(id)) {
            return homepageRepository.save(homepage);
        } else {
            return null;  // or you can throw an exception
        }
    }

    @Override
    public void deleteHomepage(Integer id) {
        homepageRepository.deleteById(id);
    }

    @Override
    public Page<HomePageDTO> getAllHomepages(Pageable pageable) {
        Specification<Homepage> specification = Specification.where(HomePageSpecification.isVisible());

        Page<Homepage> homepages = homepageRepository.findAll(specification, pageable);

        return homepages.map(homepage -> modelMapper.map(homepage, HomePageDTO.class));
    }


    @Override
    public Page<Homepage> getAllPageableHomepages(Pageable pageable, String searchTerm) {
        if(StringUtils.isBlank(searchTerm)) {
            return homepageRepository.findAll(pageable);
        } else {
            // Assuming you have a method in your repository to search by term
            return homepageRepository.findByComplexSearch(searchTerm, searchTerm, pageable);
        }
    }


    @Override
    public Homepage addImage(Integer id, String imageUrl) {
        Optional<Homepage> homepageOpt = homepageRepository.findById(id);
        if(homepageOpt.isPresent()) {
            Homepage homepage = homepageOpt.get();
            homepage.getImageUuids().add(imageUrl);
            return homepageRepository.save(homepage);
        } else {
            return null;
        }
    }

    @Override
    public Homepage removeImage(Integer id, String imageUrl) {
        Optional<Homepage> homepageOpt = homepageRepository.findById(id);
        if(homepageOpt.isPresent()) {
            Homepage homepage = homepageOpt.get();
            homepage.getImageUuids().remove(imageUrl);
            return homepageRepository.save(homepage);
        } else {
            return null;
        }
    }

    @Override
    public Homepage updateVisibility(Integer id, Boolean isVisibility) {
        Optional<Homepage> homepageOpt = homepageRepository.findById(id);
        if(homepageOpt.isPresent()) {
            Homepage homepage = homepageOpt.get();
            // Assuming you added a setter for `isVisible` in Homepage entity.
            homepage.setVisible(isVisibility);
            return homepageRepository.save(homepage);
        } else {
            return null;
        }
    }
}
