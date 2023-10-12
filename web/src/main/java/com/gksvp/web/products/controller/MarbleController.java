package com.gksvp.web.products.controller;

import com.gksvp.web.products.dto.MarbleDTO;
import com.gksvp.web.products.entity.Marble;
import com.gksvp.web.products.service.MarbleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marbles")
public class MarbleController {

    private final MarbleService marbleService;

    @Autowired
    public MarbleController(MarbleService marbleService) {
        this.marbleService = marbleService;
    }

    // Fetch all marbles for a specific company
    @GetMapping("company")
    public ResponseEntity<Page<Marble>> getAllMarbles(
            @RequestParam("companyId") Long companyId,
            @RequestParam(name = "searchTerm", required = false, defaultValue = "") String searchTerm,
            Pageable pageable) {

        try {
            Page<Marble> marbles = marbleService.getCompanyMarbles(companyId, searchTerm, pageable);

            if (marbles.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(marbles);

        } catch (Exception e) {
            // Logging the exception is also recommended
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // Get all marbles
    @GetMapping
    public ResponseEntity<List<Marble>> getAllMarbles() {
        return ResponseEntity.ok(marbleService.findAll());
    }

    // Get marble by ID
    @GetMapping("/{id}")
    public ResponseEntity<Marble> getMarbleById(@PathVariable Long id) {
        return marbleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new marble
    @PostMapping
    public ResponseEntity<Marble> createMarble(@RequestBody Marble marble) {
        return ResponseEntity.ok(marbleService.save(marble));
    }

    // Update a marble
    @PutMapping("/{id}")
    public ResponseEntity<Marble> updateMarble(@PathVariable Long id, @RequestBody Marble marble) {
        if (marbleService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        marble.setId(id); // Ensure the ID is set to prevent a new entity creation
        return ResponseEntity.ok(marbleService.save(marble));
    }

    // Delete a marble
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarble(@PathVariable Long id) {
        marbleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Get marble by type (assuming you have this functionality)
    @GetMapping("/type/{type}")
    public ResponseEntity<Marble> getMarbleByType(@PathVariable String type) {
        return marbleService.findByType(type)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get marble by isVisibleToUsers
    @GetMapping("/active-product")
    public ResponseEntity<Page<MarbleDTO>> fetchActiveProduct(Pageable pageable, String searchKeyword) {
        Page<MarbleDTO> result = marbleService.fetchActiveProduct(pageable, searchKeyword);

        if (result != null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
