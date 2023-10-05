package com.gksvp.web.exception;


public class PlantNotFoundException extends RuntimeException {
    public PlantNotFoundException(Long id) {
        super("Plant with ID " + id + " not found.");
    }
}
