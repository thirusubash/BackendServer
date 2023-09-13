package com.gksvp.web.homepage.service;

import com.gksvp.web.homepage.entity.Homepage;
import com.gksvp.web.homepage.repository.HomepageRepository;
import com.gksvp.web.media.entity.BlobImage;
import com.gksvp.web.media.service.BlobImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class HomePageServiceImpl implements HomePageService {

    private final HomepageRepository homepageRepository;
    private final BlobImageService blobImageService;

    @Autowired
    public HomePageServiceImpl(HomepageRepository homepageRepository, BlobImageService blobImageService) {
        this.homepageRepository = homepageRepository;
        this.blobImageService = blobImageService;
    }

    @Override
    public Homepage createHomepage(Homepage homepage) {
        // Implement the logic to create a homepage
        return homepageRepository.save(homepage);
    }

    @Override
    public Homepage getHomepageById(Integer id) {
        // Implement the logic to retrieve a homepage by ID
        return homepageRepository.findById(id).orElse(null);
    }

    @Override
    public List<Homepage> getAllHomepages() {
        // Implement the logic to retrieve all homepages
        return homepageRepository.findAll();
    }

    @Override
    public Homepage updateHomepage(Integer id, Homepage homepage) {
        // Implement the logic to update a homepage
        return homepageRepository.save(homepage);
    }

    @Override
    public void deleteHomepage(Integer id) {
        // Implement the logic to delete a homepage by ID
        homepageRepository.deleteById(id);
    }

    @Override
    @Transactional
    public boolean addImages(Integer id, MultipartFile[] images) throws IOException {
        Homepage homepage = homepageRepository.findById(id).orElse(null);
        if (homepage != null) {
            for (MultipartFile image : images) {
                // Store the image using BlobImageService
                BlobImage blobImage = blobImageService.storeImage(image, "Home Page");

                // Add the UUID to the homepage's uuids list
                homepage.getUuids().add(blobImage.getId().toString());
            }
            // Update the homepage in the database
            homepageRepository.save(homepage);

            return true; // Images were successfully added
        }
        return false; // Homepage with the given ID does not exist
    }


    @Override
    @Transactional
    public boolean removeImages(Integer id, String imageId, boolean softDelete) {
        Homepage homepage = homepageRepository.findById(id).orElse(null);
        if (homepage != null) {
            if (softDelete) {
                // Assuming you want to remove the image UUID from the homepage's uuids list
                homepage.getUuids().remove(imageId);
            } else {
                // Implement the logic to delete the image (you can add this logic here)
                // Assuming you want to remove the image UUID from the homepage's uuids list as well
                homepage.getUuids().remove(imageId);
                blobImageService.deleteImage(UUID.fromString(imageId));

            }
            return true; // Return true if the image was successfully removed.
        }
        return false;
    }
}
