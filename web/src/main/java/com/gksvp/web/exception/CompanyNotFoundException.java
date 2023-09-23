package com.gksvp.web.exception;
public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(Long id) {
        super("Company with ID " + id + " not found.");
    }
}
