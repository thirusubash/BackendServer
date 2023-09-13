package com.gksvp.web.homepage.service;

import com.gksvp.web.homepage.entity.Homepage;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface HomePageService {

    Homepage createHomepage(Homepage homepage);

    Homepage getHomepageById(Integer id);

    List<Homepage> getAllHomepages();

    Homepage updateHomepage(Integer id, Homepage homepage);

    void deleteHomepage(Integer id);

    boolean addImages(Integer id, MultipartFile[] images) throws IOException;

    @Transactional
    boolean removeImages(Integer id, String imageId, boolean softDelete);
}
