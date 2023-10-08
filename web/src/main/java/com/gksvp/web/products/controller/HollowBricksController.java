package com.gksvp.web.products.controller;

import com.gksvp.web.products.entity.HollowBricks;
import com.gksvp.web.products.entity.Marble;
import com.gksvp.web.products.service.HollowBricksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hollow-bricks")
public class HollowBricksController {

    @Autowired
    private HollowBricksService hollowBricksService;
    @GetMapping("company")
    public ResponseEntity<Page<HollowBricks>> getAllGranitesByCompany(
            @RequestParam("companyId") Long companyId,
            @RequestParam(name = "searchTerm", required = false, defaultValue = "") String searchTerm,
            Pageable pageable) {

        try {
            Page<HollowBricks> hollowBricks = hollowBricksService.getCompany(companyId, searchTerm, pageable);

            if (hollowBricks.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(hollowBricks);

        } catch (Exception e) {
            // Logging the exception is also recommended
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping
    public List<HollowBricks> getAllHollowBricks() {
        return hollowBricksService.getAllHollowBricks();
    }

    @GetMapping("/{id}")
    public HollowBricks getHollowBrickById(@PathVariable Long id) {
        return hollowBricksService.getHollowBrickById(id);
    }

    @PostMapping
    public HollowBricks addHollowBrick(@RequestBody HollowBricks hollowBricks) {
        return hollowBricksService.saveHollowBrick(hollowBricks);
    }

    @DeleteMapping("/{id}")
    public void deleteHollowBrick(@PathVariable Long id) {
        hollowBricksService.deleteHollowBrick(id);
    }

    // ... Add endpoints for other operations like updating a HollowBrick, etc ...

}
