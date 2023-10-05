package com.gksvp.web.company.controller;

import com.gksvp.web.company.dto.BasicCompanyDTO;
import com.gksvp.web.company.dto.EmployeeDTO;
import com.gksvp.web.company.entity.*;
import com.gksvp.web.company.service.CompanyService;
import com.gksvp.web.company.service.CompanyTemplate;
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

private  final  CompanyTemplate companyTemplate;
    public CompanyController(CompanyService companyService, CompanyTemplate companyTemplate) {
        this.companyService = companyService;
        this.companyTemplate = companyTemplate;
    }



    // Get companies with pagination and search
    @GetMapping
    public ResponseEntity<Page<BasicCompanyDTO>> getAllCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BasicCompanyDTO> companies;

        if (keyword != null && !keyword.isEmpty()) {
            companies = companyService.searchCompaniesByName(keyword, pageable);
        } else {
            companies = companyService.getAllCompanies(pageable);
        }

        return ResponseEntity.ok(companies);
    }


    // Endpoint to get all employees of a company with pagination and search
    @GetMapping("/{companyId}/employees")
    public ResponseEntity<Page<EmployeeDTO>> getCompanyEmployees(
            @PathVariable Long companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {

        Pageable pageable = PageRequest.of(page, size);
        Page<EmployeeDTO> employees;

        if (keyword != null && !keyword.trim().isEmpty()) {
            employees = companyService.searchEmployeesByCompanyAndKeyword(companyId, keyword, pageable);
        } else {
            employees = companyService.getEmployeesByCompanyId(companyId, pageable);
        }

        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{companyId}/plants")
    public ResponseEntity<Page<Plant>> getCompanyplants(
            @PathVariable Long companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Plant> plants;

        if (keyword != null && !keyword.trim().isEmpty()) {
            plants = companyService.searchPlantsByCompanyAndKeyword(companyId, keyword, pageable);
        } else {
            plants = companyService.getPlantsByCompanyId(companyId, pageable);
        }

        return ResponseEntity.ok(plants);
    }
 
    @GetMapping("/{companyId}/bank-details")
    public ResponseEntity<Page<BankDetails>> getCompanyBankdetails(
            @PathVariable Long companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {

        Pageable pageable = PageRequest.of(page, size);
        Page<BankDetails> bankDetails;

        if (keyword != null && !keyword.trim().isEmpty()) {
            bankDetails = companyService.searchBankdetailsByCompanyAndKeyword(companyId, keyword, pageable);
        } else {
            bankDetails = companyService.getBankdetailsByCompanyId(companyId, pageable);
        }

        return ResponseEntity.ok(bankDetails);
    }

    @GetMapping("/{companyId}/address")
    public ResponseEntity<Page<CompanyAddress>> getCompanyAddress(
            @PathVariable Long companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {

        Pageable pageable = PageRequest.of(page, size);
        Page<CompanyAddress> companyAddresses;

        if (keyword != null && !keyword.trim().isEmpty()) {
            companyAddresses = companyService.searchCompanyAddressByCompanyAndKeyword(companyId, keyword, pageable);
        } else {
            companyAddresses = companyService.getCompanyAddressByCompanyId(companyId, pageable);
        }

        return ResponseEntity.ok(companyAddresses);
    }





    @GetMapping("/{companyId}")
    public Company getCompanyById(@PathVariable Long companyId) {
        return companyService.getCompanyById(companyId);
    }

    @PostMapping
    public Company createCompany(@RequestBody Company company) {
        return companyTemplate.createCompanyWithDefaults(company);
    }

    @PutMapping("/{companyId}")
    public Company updateCompany(@PathVariable Long companyId, @RequestBody Company updatedCompany) {
        return companyService.updateCompany(companyId, updatedCompany);
    }

    @DeleteMapping("/{companyId}")
    public void deleteCompany(@PathVariable Long companyId) {
        companyService.deleteCompany(companyId);
    }

    // Add endpoints for handling employees within a company

    @PostMapping("/{companyId}/employees")
    public Company createEmployee(@PathVariable Long companyId, @RequestBody Employee employee) {
        return companyService.updateOrCreateEmployee(companyId, employee);
    }

    @PutMapping("/{companyId}/employees/{employeeId}")
    public Company updateEmployee(@PathVariable Long companyId, @PathVariable Long employeeId, @RequestBody Employee updatedEmployee) {
        return companyService.updateOrCreateEmployee(companyId, updatedEmployee);
    }

    @DeleteMapping("/{companyId}/employees/{employeeId}")
    public String removeEmployee(@PathVariable Long companyId, @PathVariable Long employeeId) {
        return companyService.removeEmployee(employeeId);
    }

    // Add endpoints for handling plants within a company

    @PostMapping("/{companyId}/plants")
    public Company createPlant(@PathVariable Long companyId, @RequestBody Plant plant) {
        return companyService.updateOrCreatePlant(companyId, plant);
    }

    @PutMapping("/{companyId}/plants/{plantId}")
    public Company updatePlant(@PathVariable Long companyId, @PathVariable Long plantId, @RequestBody Plant updatedPlant) {
        return companyService.updateOrCreatePlant(companyId, updatedPlant);
    }

    @DeleteMapping("/{companyId}/plants/{plantId}")
    public String removePlant(@PathVariable Long companyId, @PathVariable Long plantId) {
        return companyService.removePlant(companyId, plantId);
    }

    // Add endpoints for handling suppliers within a company

    @PostMapping("/{companyId}/suppliers")
    public Company createSupplier(@PathVariable Long companyId, @RequestBody Supplier supplier) {
        return companyService.updateOrCreateSuppliers(companyId, supplier);
    }

    @PostMapping("/{companyId}/address")
    public Company addAddress(@PathVariable Long companyId, @RequestBody CompanyAddress address) {
        return companyService.updateOrCreateCompanyAddress(companyId, address);
    }

    @PutMapping("/{companyId}/address/{addressId}")
    public Company updateAddress(@PathVariable Long companyId, @PathVariable Long addressId, @RequestBody CompanyAddress address) {
        return companyService.updateOrCreateCompanyAddress(companyId, address);
    }

    @DeleteMapping("/{companyId}/address/{addressId}")
    public String removeAddress(@PathVariable Long companyId, @PathVariable Long addressId) {
        return companyService.removeAddress(companyId, addressId);  // Changed 'supplierId' to 'addressId'
    }

    @PatchMapping("/{companyId}/address/{addressId}/status")
    public Boolean updateAddressStatus(
            @PathVariable Long companyId,
            @PathVariable Long addressId,
            @RequestParam Boolean status) {
        return companyService.updateAddressStatus(companyId, addressId, status);
    }

    @PatchMapping("/{companyId}/employee/{employeeId}/status")
    public Boolean updateEmployeeStatus(
            @PathVariable Long companyId,
            @PathVariable Long employeeId,
            @RequestParam Boolean status) {
        return companyService.updateEmployeeStatus(companyId, employeeId, status);
    }

    @PatchMapping("/{companyId}/plants/{plantId}/status")
    public Boolean updatePlantStatus(
            @PathVariable Long companyId,
            @PathVariable Long plantId,
            @RequestParam Boolean status) {
        return companyService.updatePlantStatus(companyId, plantId, status);
    }

    @PatchMapping("/{companyId}/bank-details/{bankId}/status")
    public Boolean updateBankDetailStatus(
            @PathVariable Long companyId,
            @PathVariable Long bankId,
            @RequestParam Boolean status) {
        return companyService.updateBankDetailsStatus(companyId, bankId, status);
    }


    @PatchMapping("/{companyId}/status")
    public Boolean updateStatus(
            @PathVariable Long companyId,
            @RequestParam Boolean status) {
        return companyService.updateStatus(companyId,  status);
    }

    @PostMapping("/{companyId}/bank-details")
    public String addOrUpdateBankDetails(@PathVariable Long companyId,
                                         @RequestBody BankDetails bankDetails) {
        return companyService.updateOrAddBankDetails(companyId, bankDetails);
    }

    @DeleteMapping("/{companyId}/bank-details/{bankDetailsId}")
    public String removeBankDetails(@PathVariable Long companyId,
                                    @PathVariable Long bankDetailsId) {
        return companyService.removeBankDetails(companyId, bankDetailsId);
    }

    @PostMapping("/{companyId}/location")
    public String addLocation(@PathVariable Long companyId,
                                         @RequestBody CompanyLocation location) {
        return companyService.updateOrcreateLocation(companyId, location);
    }

    @DeleteMapping("/{companyId}/location/{locationId}")
    public String removeLocation(@PathVariable Long companyId,
                                    @PathVariable Long locationId) {
        return companyService.removeLocation(companyId, locationId);
    }







}
