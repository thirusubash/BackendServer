package com.gksvp.web.company.service;

import com.gksvp.web.company.entity.Company;
import com.gksvp.web.company.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceCurd {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceCurd(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    // Create
    @Transactional
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    // Read (by ID)
    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    // Read (All)
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    // Update
    @Transactional
    public Company updateCompany(Company company) {
        if (companyRepository.existsById(company.getId())) {
            return companyRepository.save(company);
        } else {
            throw new RuntimeException("Company not found with ID: " + company.getId());
        }
    }

    // Delete (by ID)
    @Transactional
    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }
}
