package com.gksvp.web.homepage.entity;

import jakarta.persistence.*;
import java.util.List;

import lombok.*;

@Entity
@Data
public class Homepage {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String title; // Corrected variable name to "title"
  private String type;
  private String button1Title;
  private String button2Title;
  private String button1RedirectUrl; // Corrected variable name to "button1RedirectUrl"
  private String button2RedirectUrl; // Corrected variable name to "button2RedirectUrl"
  private String postTitle;
  private String siteTitle;
  private int sortingOrder;
  private String description; // Corrected variable name to "description"
  private String message;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "homepage_uuids", joinColumns = @JoinColumn(name = "homepage_id"))
  @Column(name = "uuid")
  private List<String> uuids; // Change the data type to String
}
