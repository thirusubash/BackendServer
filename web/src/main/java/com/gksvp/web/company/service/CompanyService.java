package com.gksvp.web.company.service;

import com.gksvp.web.company.entity.Company;
import com.gksvp.web.company.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Page<Company> searchCompaniesByName(String keyword, Pageable pageable) {
        return companyRepository.findByCompanyNameContainingIgnoreCase(keyword, pageable);
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(Long id, Company updatedCompany) {
        Company existingCompany = companyRepository.findById(id).orElse(null);
        if (existingCompany != null) {
            existingCompany.setCompanyName(updatedCompany.getCompanyName());
            // Update other properties as needed
            return companyRepository.save(existingCompany);
        }
        return null; // Handle non-existing company
    }

    public void deleteCompany(Long companyId) {
        companyRepository.deleteById(companyId);
    }

    public Page<Company> getAllCompanies(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    // Add other business logic methods as needed
}
