package com.gksvp.web.homepage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Homepage {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @NotNull
  @Size(min = 1, max = 100)
  private String title;

  private String type;
  @NotNull
  private String primaryButtonTitle;
  private String secondaryButtonTitle;
  @NotNull
  private String primaryButtonRedirectUrl;
  private String secondaryButtonRedirectUrl;
  private String postHeadline;
  private String websiteTitle;
  @NotNull
  private int sortOrder;
  private String detailedDescription;
  private String notificationMessage;
  private boolean isVisible=false;

  @ElementCollection
  @CollectionTable(name = "homepage_uuids") // Specify the name of the table
  private List<String> imageUuids;

  // Auditing fields
  @CreatedBy
  @Column(updatable = false)
  private String createdBy;
  @CreatedDate
  @Column(updatable = false)
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private LocalDateTime created;
  @LastModifiedBy
  private String lastModifiedBy;
  @LastModifiedDate
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updated;
}
