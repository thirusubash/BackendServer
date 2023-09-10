package com.gksvp.web.media.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

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

    // Binary data of the image (I  choose  Blob)
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageData;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] thumbnail;

    private String alt;
    private String description;

}
