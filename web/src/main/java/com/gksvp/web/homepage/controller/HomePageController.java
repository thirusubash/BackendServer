package com.gksvp.web.homepage.controller;

import com.gksvp.web.homepage.entity.Homepage;
import com.gksvp.web.homepage.service.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/homepages")
@CrossOrigin(origins = "https://localhost:8080", allowCredentials = "true")
public class HomePageController {

    private final HomePageService homePageService;

    @Autowired
    public HomePageController(HomePageService homePageService) {
        this.homePageService = homePageService;
    }

    @PostMapping
    public ResponseEntity<Homepage> createHomepage(@RequestBody Homepage homepage) {
        Homepage createdHomepage = homePageService.createHomepage(homepage);
        return new ResponseEntity<>(createdHomepage, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Homepage> getHomepageById(@PathVariable Integer id) {
        Homepage homepage = homePageService.getHomepageById(id);
        if (homepage != null) {
            return new ResponseEntity<>(homepage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Homepage>> getAllHomepages() {
        List<Homepage> homepages = homePageService.getAllHomepages();
        return new ResponseEntity<>(homepages, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Homepage> updateHomepage(@PathVariable Integer id, @RequestBody Homepage homepage) {
        Homepage updatedHomepage = homePageService.updateHomepage(id, homepage);
        if (updatedHomepage != null) {
            return new ResponseEntity<>(updatedHomepage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHomepage(@PathVariable Integer id) {
        homePageService.deleteHomepage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<Void> addImages(@PathVariable Integer id, @RequestParam("images") MultipartFile[] images) {
        try {
            boolean imagesAdded = homePageService.addImages(id, images);
            if (imagesAdded) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}/images/{imageId}")
    public ResponseEntity<Void> removeImages(
            @PathVariable Integer id,
            @PathVariable String imageId,
            @RequestParam(name = "softDelete", defaultValue = "false") boolean softDelete) {
        boolean imagesRemoved = homePageService.removeImages(id, imageId, softDelete);
        if (imagesRemoved) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
