package com.gksvp.web.media.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "media_files")
public class BlobImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String fileName;
    private String fileType;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    // Binary data of the image (you can choose byte[] or Blob)
    @Lob
    @Column(name = "image_data", columnDefinition = "LONGBLOB")
    private byte[] imageData;
    @Lob
    @Column(name = "thumbnail_data", columnDefinition = "MEDIUMBLOB")
    private byte[] tubmbnail;

    private String alt;
    private String description;

}
