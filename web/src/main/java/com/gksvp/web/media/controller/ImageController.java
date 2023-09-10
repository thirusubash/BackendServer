package com.gksvp.web.media.controller;

import com.gksvp.web.media.entity.BlobImage;
import com.gksvp.web.media.service.BlobImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private BlobImageService blobImageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,
            @RequestParam("description") String description) {
        String filename = file.getOriginalFilename();
        return ResponseEntity.ok("Image uploaded successfully. ID: " + filename);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable UUID id) {
        BlobImage blobImage = blobImageService.getImage(id);
        if (blobImage != null) {
            ByteArrayResource resource = new ByteArrayResource(blobImage.getImageData());
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + blobImage.getFileName())
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
