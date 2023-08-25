package com.gksvp.web.media.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.gksvp.web.media.entity.BlobImage;
import com.gksvp.web.media.service.BlobImageService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/short-image")
@CrossOrigin(origins = "https://localhost:8080", allowCredentials = "true")
public class BlobImageController {

    private final BlobImageService blobImageService;

    public BlobImageController(BlobImageService blobImageService) {
        this.blobImageService = blobImageService;
    }

    @PostMapping()
    public ResponseEntity<BlobImage> uploadBlobImage(@RequestParam("file") MultipartFile file,
            @RequestParam("description") String description) {
        try {
            BlobImage uploadedImage = blobImageService.storeImage(file, description);
            return ResponseEntity.ok(uploadedImage);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlobImage> getThumbnail(@PathVariable UUID id) {
        BlobImage thumbnail = blobImageService.getThumbnailData(id);
        if (thumbnail != null) {
            return ResponseEntity.ok(thumbnail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<BlobImage> getImage(@PathVariable UUID id) {
        BlobImage image = blobImageService.getImage(id);
        if (image != null) {
            return ResponseEntity.ok(image);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/onlyimage/{id}")
    public ResponseEntity<byte[]> getOnlyImage(@PathVariable UUID id) {
        byte[] imageData = blobImageService.getOnlyImage(id);
        if (imageData != null) {
            return ResponseEntity.ok(imageData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
