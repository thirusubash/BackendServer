package com.gksvp.web.media.repository;

import com.gksvp.web.media.entity.BlobImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BlobImageRepository extends JpaRepository<BlobImage, UUID> {
}
