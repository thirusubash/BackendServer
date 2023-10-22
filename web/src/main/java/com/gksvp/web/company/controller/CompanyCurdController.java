package com.gksvp.web.company.controller;

import com.gksvp.web.company.entity.Company;
import com.gksvp.web.company.service.CompanyServiceCurd;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test/companies")
public class CompanyCurdController {

    private final CompanyServiceCurd companyService;

    public CompanyCurdController(CompanyServiceCurd companyService) {
        this.companyService = companyService;
    }

    // Create a new Company
    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        Company savedCompany = companyService.createCompany(company);
        return new ResponseEntity<>(savedCompany, HttpStatus.CREATED);
    }

    // Get a Company by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        return companyService.findById(id)
                .map(company -> new ResponseEntity<>(company, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get all Companies
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.findAll();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    // Update an existing Company
    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody Company company) {
        if (!id.equals(company.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Company updatedCompany = companyService.updateCompany(company);
        return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
    }

    // Delete a Company by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
