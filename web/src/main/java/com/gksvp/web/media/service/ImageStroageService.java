package com.gksvp.web.media.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStroageService {
    String storeFile(MultipartFile file);
}
