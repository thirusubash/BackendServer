package com.gksvp.web.products.service;

import com.gksvp.web.products.entity.HollowBricks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface HollowBricksService {

    List<HollowBricks> getAllHollowBricks();

    HollowBricks getHollowBrickById(Long id);

    HollowBricks saveHollowBrick(HollowBricks hollowBricks);

    void deleteHollowBrick(Long id);

    Page<HollowBricks> getCompany(Long companyId, String searchTerm, Pageable pageable);

    // ... Add any other HollowBricks-related service methods here ...
}
