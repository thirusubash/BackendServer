package com.gksvp.web.media.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gksvp.web.media.entity.BlobImage;

public interface BlobImageRepository extends JpaRepository<BlobImage, UUID> {
}
