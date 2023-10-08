package com.gksvp.web.products.service.impl;


import com.gksvp.web.products.entity.HollowBricks;
import com.gksvp.web.products.entity.Marble;
import com.gksvp.web.products.repository.HollowBrickRepository;
import com.gksvp.web.products.service.HollowBricksService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HollowBricksServiceImpl implements HollowBricksService {

    private HollowBrickRepository hollowBricksRepository;

    @Override
    public List<HollowBricks> getAllHollowBricks() {
        return hollowBricksRepository.findAll();
    }

    @Override
    public HollowBricks getHollowBrickById(Long id) {
        return hollowBricksRepository.findById(id).orElse(null);
    }

    @Override
    public HollowBricks saveHollowBrick(HollowBricks hollowBricks) {
        return hollowBricksRepository.save(hollowBricks);
    }

    @Override
    public void deleteHollowBrick(Long id) {
        hollowBricksRepository.deleteById(id);
    }


    @Override
    public Page<HollowBricks> getCompany(Long companyId, String searchTerm, Pageable pageable) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return hollowBricksRepository.findByCompanyId(companyId, pageable);
        } else {
            return hollowBricksRepository.findByCompanyIdAndNameContainingIgnoreCase(companyId, searchTerm, pageable);
        }
    }

}
