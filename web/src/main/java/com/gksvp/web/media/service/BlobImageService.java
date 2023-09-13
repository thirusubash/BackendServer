package com.gksvp.web.media.service;

import com.gksvp.web.media.entity.BlobImage;
import com.gksvp.web.media.repository.BlobImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class BlobImageService {

    private final BlobImageRepository blobImageRepository;

    private final ThumbnailGenerator thumbnailGenerator;

    public BlobImageService(BlobImageRepository blobImageRepository, ThumbnailGenerator thumbnailGenerator) {
        this.blobImageRepository = blobImageRepository;
        this.thumbnailGenerator = thumbnailGenerator;
    }

    @Transactional
    public BlobImage storeImage(MultipartFile file, String description) throws IOException {
        BlobImage blobImage = new BlobImage();
        blobImage.setFileName(file.getOriginalFilename());
        blobImage.setFileType(file.getContentType());
        blobImage.setImageData(file.getBytes());

        // Generate and set the thumbnail data
        byte[] thumbnailData = thumbnailGenerator.generateThumbnail(file.getBytes(), file.getContentType());
        blobImage.setThumbnail(thumbnailData);

        blobImage.setDescription(description);

        return blobImageRepository.save(blobImage);
    }

    @Transactional(readOnly = true)
    public BlobImage getImage(UUID id) {
        return blobImageRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public byte[] getOnlyImage(UUID id) {
        Optional<BlobImage> blobImageOptional = blobImageRepository.findById(id);
        if (blobImageOptional.isPresent()) {
            BlobImage blobImage = blobImageOptional.get();

            return blobImage.getImageData();
        }
        return null;
    }

    @Transactional(readOnly = true)
    public BlobImage getThumbnailData(UUID id) {
        Optional<BlobImage> optionalBlobImage = blobImageRepository.findById(id);
        if (optionalBlobImage.isPresent()) {
            BlobImage blobImage = optionalBlobImage.get();
            blobImage.setImageData(null); // Set imageData to null
            return blobImage;
        }
        return null;
    }

    @Transactional
    public void deleteImage(UUID id) {
        Optional<BlobImage> optionalBlobImage = blobImageRepository.findById(id);
        if (optionalBlobImage.isPresent()) {
            BlobImage blobImage = optionalBlobImage.get();
            blobImageRepository.delete(blobImage);
        }
    }
}
