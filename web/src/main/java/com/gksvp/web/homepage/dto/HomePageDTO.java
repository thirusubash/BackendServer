package com.gksvp.web.homepage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class HomePageDTO {

    private Integer id;
    private String title;
    private String type;
    private String primaryButtonTitle;
    private String secondaryButtonTitle;
    private String primaryButtonRedirectUrl;
    private String secondaryButtonRedirectUrl;
    private String postHeadline;
    private String websiteTitle;
    private String detailedDescription;
    private String notificationMessage;
    private List<String> imageUuids;


}
