package com.gksvp.web.company.controller;

import com.gksvp.web.company.entity.Company;
import com.gksvp.web.company.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "https://localhost:8080", allowCredentials = "true")
public class CompanyController {
    private final CompanyService companyService;


    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }



    // Get companies with pagination and search
    @GetMapping
    public ResponseEntity<Page<Company>> getAllCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Company> companies;

        if (keyword != null && !keyword.isEmpty()) {
            companies = companyService.searchCompaniesByName(keyword, pageable);
        } else {
            companies = companyService.getAllCompanies(pageable);
        }

        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{companyId}")
    public Company getCompanyById(@PathVariable Long companyId) {
        return companyService.getCompanyById(companyId);
    }

    @PostMapping
    public Company createCompany(@RequestBody Company company) {
        return companyService.createCompany(company);
    }

    @PutMapping("/{companyId}")
    public Company updateCompany(@PathVariable Long companyId, @RequestBody Company updatedCompany) {
        return companyService.updateCompany(companyId, updatedCompany);
    }

    @DeleteMapping("/{companyId}")
    public void deleteCompany(@PathVariable Long companyId) {
        companyService.deleteCompany(companyId);
    }

    // Add other endpoints as needed
}
