package com.gksvp.web.company.service;


import com.gksvp.web.company.entity.Plant;
import com.gksvp.web.company.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantService {
    private final PlantRepository plantRepository;

    @Autowired
    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public List<Plant> getAllPlants() {
        return plantRepository.findAll();
    }

    public Plant getPlantById(Long plantId) {
        return plantRepository.findById(plantId).orElse(null);
    }

    public Plant createPlant(Plant plant) {
        return plantRepository.save(plant);
    }

    public Plant updatePlant(Long plantId, Plant updatedPlant) {
        if (plantRepository.existsById(plantId)) {
            updatedPlant.setName(updatedPlant.getName());
            return plantRepository.save(updatedPlant);
        }
        return null; // Handle non-existing plant
    }

    public void deletePlant(Long plantId) {
        plantRepository.deleteById(plantId);
    }

    // Add other business logic methods as needed
}
