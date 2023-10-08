package com.gksvp.web.homepage.service;

import com.gksvp.web.homepage.dto.HomePageDTO;
import com.gksvp.web.homepage.entity.Homepage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HomePageService {

    Homepage createHomepage(Homepage homepage);

    Homepage getHomepageById(Integer id);
    Homepage updateHomepage(Integer id, Homepage homepage);

    void deleteHomepage(Integer id);
    Page<HomePageDTO> getAllHomepages(Pageable pageable);
    Page<Homepage> getAllPageableHomepages(Pageable pageable , String searchTerm);

    Homepage addImage(Integer id, String imageUrl);
    public Homepage removeImage(Integer id, String imageUrl);


    Homepage updateVisibility(Integer id, Boolean isVisibility);
}
