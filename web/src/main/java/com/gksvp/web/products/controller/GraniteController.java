package com.gksvp.web.products.controller;
import com.gksvp.web.products.entity.Granite;
import com.gksvp.web.products.service.GraniteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/granites")
public class GraniteController {

    @Autowired
    private GraniteService graniteService;

    @GetMapping
    public List<Granite> getAllGranites() {
        return graniteService.getAllGranites();
    }

    @GetMapping("/{id}")
    public Granite getGraniteById(@PathVariable Long id) {
        return graniteService.getGraniteById(id).orElse(null);
    }

    @PostMapping
    public Granite createGranite(@RequestBody Granite granite) {
        return graniteService.saveOrUpdateGranite(granite);
    }

    @PutMapping("/{id}")
    public Granite updateGranite(@PathVariable Long id, @RequestBody Granite granite) {
        if (graniteService.getGraniteById(id).isPresent()) {
            return graniteService.saveOrUpdateGranite(granite);
        }
        return null; // Consider a better error handling strategy, e.g., throwing exceptions and handling them globally.
    }

    @DeleteMapping("/{id}")
    public void deleteGranite(@PathVariable Long id) {
        graniteService.deleteGranite(id);
    }
}
