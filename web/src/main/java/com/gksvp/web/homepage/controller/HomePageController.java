package com.gksvp.web.homepage.controller;

import com.gksvp.web.homepage.dto.HomePageDTO;
import com.gksvp.web.homepage.entity.Homepage;
import com.gksvp.web.homepage.service.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<HomePageDTO>> getAllHomepages(@PageableDefault(size = 10) Pageable pageable) {
        Page<HomePageDTO> homepages = homePageService.getAllHomepages(pageable);
        return new ResponseEntity<>(homepages, HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<Page<Homepage>> getAllHomepagesWithPage(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String search) {
        Page<Homepage> homepages=homePageService.getAllPageableHomepages(pageable, search);
        return new ResponseEntity<>(homepages, HttpStatus.OK);
    }

    @PatchMapping("/add-image")
    public ResponseEntity<Homepage> addImage(
            @RequestParam Integer id,
            @RequestParam String image) {
        Homepage homepage = homePageService.addImage(id, image);
        return ResponseEntity.ok(homepage);
    }

    @PatchMapping("/remove-image")
    public ResponseEntity<Homepage> removeImage(
            @RequestParam Integer id,
            @RequestParam String image) {
        Homepage homepage = homePageService.removeImage(id, image);
        return ResponseEntity.ok(homepage);
    }

    @PatchMapping("/status")
    public ResponseEntity<Homepage> updateVisibility(
            @RequestParam Integer id,
            @RequestParam Boolean isVisibility) {
        Homepage homepage = homePageService.updateVisibility(id, isVisibility); // Assuming the method is present in HomePageService
        return ResponseEntity.ok(homepage);
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




}
