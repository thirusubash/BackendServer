package com.gksvp.web.homepage.entity;



import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Homepage {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String title;
  private String type;
  private String button1Title;
  private String button2Title;
  private String button1RedirectUrl;
  private String button2RedirectUrl;
  private String postTitle;
  private String siteTitle;
  private int sortingOrder;
  private String description;
  private String message;

  @ElementCollection
  @CollectionTable(name = "homepage_uuids") // Specify the name of the table
  private List<String> uuids;
}


