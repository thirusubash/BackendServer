package com.gksvp.web.company.controller;

import com.gksvp.web.company.entity.Plant;
import com.gksvp.web.company.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plants")
@CrossOrigin(origins = "https://localhost:8080", allowCredentials = "true")
public class PlantController {
    private final PlantService plantService;

    @Autowired
    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @GetMapping
    public Page<Plant> getAllPlants(Pageable pageable
    ) {
        return plantService.getAllPlants(pageable);
    }

    @GetMapping("/{plantId}")
    public Plant getPlantById(@PathVariable Long plantId) {
        return plantService.getPlantById(plantId);
    }

    @PostMapping
    public Plant createPlant(@RequestBody Plant plant) {
        return plantService.createPlant(plant);
    }

    @PutMapping("/{plantId}")
    public Plant updatePlant(@PathVariable Long plantId, @RequestBody Plant updatedPlant) {
        return plantService.updatePlant(plantId, updatedPlant);
    }

    @DeleteMapping("/{plantId}")
    public void deletePlant(@PathVariable Long plantId) {
        plantService.deletePlant(plantId);
    }

    // Add other endpoints as needed
}
