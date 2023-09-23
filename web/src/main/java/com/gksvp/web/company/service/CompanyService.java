package com.gksvp.web.company.service;

import com.gksvp.web.company.entity.*;
import com.gksvp.web.company.repository.*;
import com.gksvp.web.exception.CompanyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final  EmployeeRepository employeeRepository;
    private final SupplierRepository supplierRepository;

    private final PlantRepository plantRepository;
private final CompanyAddressRepository companyAddressRepository;

private final BankDetailsRepository bankDetailsRepository;
    private final CompanyLocationRepository companyLocationRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository, SupplierRepository supplierRepository, PlantRepository plantRepository, CompanyAddressRepository companyAddressRepository, BankDetailsRepository bankDetailsRepository, CompanyLocationRepository companyLocationRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
        this.supplierRepository = supplierRepository;
        this.plantRepository = plantRepository;
        this.companyAddressRepository = companyAddressRepository;
        this.bankDetailsRepository = bankDetailsRepository;
        this.companyLocationRepository = companyLocationRepository;
    }

    public Page<Employee> getEmployeesByCompanyId(Long companyId, Pageable pageable) {
        // Fetch paginated employees for a specific company
        return employeeRepository.findByCompanyId(companyId, pageable);
    }

    public Page<Employee> searchEmployeesByCompanyAndKeyword(Long companyId, String keyword, Pageable pageable) {
        // Search and fetch paginated employees for a specific company based on a keyword
        return employeeRepository.findByCompanyIdAndFirstNameContainingOrCompanyIdAndLastNameContaining(
                companyId, keyword, companyId, keyword, pageable);
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
        Company existingCompany = companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(id));
        if (existingCompany != null) {
            existingCompany.setCompanyId(updatedCompany.getCompanyId());
            existingCompany.setCompanyName(updatedCompany.getCompanyName());
            existingCompany.setBusinessType(updatedCompany.getBusinessType());
            existingCompany.setRegistrationNumber(updatedCompany.getRegistrationNumber());
            existingCompany.setRegistrationAuthority(updatedCompany.getRegistrationAuthority());
            existingCompany.setDateOfIncorporation(updatedCompany.getDateOfIncorporation());
            existingCompany.setBusinessActivities(updatedCompany.getBusinessActivities());
            existingCompany.setIndustryClassification(updatedCompany.getIndustryClassification());
            existingCompany.setFinancialStatements(updatedCompany.getFinancialStatements());
            existingCompany.setContactNumber(updatedCompany.getContactNumber());
            existingCompany.setEmail(updatedCompany.getEmail());
            existingCompany.setLlpNumber(updatedCompany.getLlpNumber());
            existingCompany.setTrademarkAndIP(updatedCompany.getTrademarkAndIP());
            existingCompany.setInsuranceInformation(updatedCompany.getInsuranceInformation());
            existingCompany.setCompanyAddresses(updatedCompany.getCompanyAddresses());
            existingCompany.setPlants(updatedCompany.getPlants());
            existingCompany.setEmployees(updatedCompany.getEmployees());
            existingCompany.setSuppliers(updatedCompany.getSuppliers());
            existingCompany.setLocations(updatedCompany.getLocations());
            existingCompany.setBankAccounts(updatedCompany.getBankAccounts());

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


    public Company updateOrCreateEmployee(Long companyId, Employee employee) {
        // Fetch the company by its ID
        Company company = companyRepository.findById(companyId).orElse(null);

        // If the company doesn't exist, return null or handle as needed
        if (company == null) {
            return null;
        }

        // If the employee has an ID, search for an existing employee within the company
        if (employee.getId() != null) {
            for (Employee existingEmployee : company.getEmployees()) {
                if (existingEmployee.getId().equals(employee.getId())) {
                    // Update details of the found employee
                    existingEmployee.setFirstName(employee.getFirstName());
                    existingEmployee.setLastName(employee.getLastName());
                    existingEmployee.setDesignation(employee.getDesignation());
                    existingEmployee.setEmployeeCode(employee.getEmployeeCode());
                    existingEmployee.setHireDate(employee.getHireDate());
                    existingEmployee.setSalary(employee.getSalary());
                    existingEmployee.setPassword(employee.getPassword());
                    // ... add other fields as necessary

                    // Save the updated employee
                    employeeRepository.save(existingEmployee);
                    return company;
                }
            }
        }

        // If employee doesn't have an ID or wasn't found, add as a new employee
        employee.setCompany(company);  // Set the relationship
        company.getEmployees().add(employee);
        employeeRepository.save(employee);  // Save the new employee

        return company;
    }



    public String removeEmployee(Long employeeId) {
        // Find the employee by their ID
        Employee employeeToRemove = employeeRepository.findById(employeeId).orElse(null);

        if (employeeToRemove != null) {
            // Get the company to which the employee belongs
            Company company = employeeToRemove.getCompany();

            if (company != null) {
                // Remove the employee from the company's list of employees
                company.getEmployees().remove(employeeToRemove);

                // Save the company to update the employee list
                companyRepository.save(company);

                // Finally, delete the employee from the repository
                employeeRepository.deleteById(employeeId);

                return "Ok";
            }
        }

        // Handle the case where the employee with the given ID does not exist or is not associated with any company
        return "Employee not found";
    }


    public Company updateOrCreatePlant(Long companyId, Plant plant) {
        // Find the company by its ID
        Company company = companyRepository.findById(companyId).orElse(null);

        if (company != null) {
            if (plant.getId() == null) {
                // Logic to create a new plant within the company
                // For example, you can add the plant to the company's list of plants
                plant.setCompany(company);
                company.getPlants().add(plant);
            } else {
                // Logic to update the plant within the company
                // You may need to find the existing plant, update its information, and save it
                Plant existingPlant = company.getPlants()
                        .stream()
                        .filter(p -> p.getId().equals(plant.getId()))
                        .findFirst()
                        .orElse(null);

                if (existingPlant != null) {
                    // Update the plant's information
                    existingPlant.setName(plant.getName());
                    existingPlant.setLocation(plant.getLocation());
                    // Update other plant properties as needed
                }
            }

            // Save the updated or modified company
            return companyRepository.save(company);
        }

        // Handle the case where the company with the given ID does not exist
        return null; // or throw an exception
    }


    public String removePlant(Long companyId, Long plantId) {
        // Find the company by its ID
        Company company = companyRepository.findById(companyId).orElse(null);

        if (company != null) {
            // Find the plant by its ID within the company's list of plants
            Plant plantToRemove = company.getPlants()
                    .stream()
                    .filter(plant -> plant.getId().equals(plantId))
                    .findFirst()
                    .orElse(null);

            if (plantToRemove != null) {
                // Remove the plant from the company's list of plants
                company.getPlants().remove(plantToRemove);

                // Optionally, you can set the plant's company reference to null
                plantToRemove.setCompany(null);

                // Save the updated company
                companyRepository.save(company);

                // Return a success message or status
                return "Plant removed successfully.";
            } else {
                // Handle the case where the plant with the given ID is not found within the company
                return "Plant not found within the company.";
            }
        }

        // Handle the case where the company with the given ID does not exist
        return "Company not found.";
    }


    public Company updateOrCreateSuppliers(Long companyId, Supplier supplier) {
        // Find the company by its ID
        Company company = companyRepository.findById(companyId).orElse(null);

        if (company != null) {
            // Encode the supplier's name
            String encodedName = supplier.getName();
            supplier.setName(encodedName);

            // Check if the Supplier ID is not null
            if (supplier.getId() != null) {
                // Logic to update the supplier within the company
                Supplier existingSupplier = company.getSuppliers()
                        .stream()
                        .filter(s -> s.getId().equals(supplier.getId()))
                        .findFirst()
                        .orElse(null);

                if (existingSupplier != null) {
                    // Update the existing supplier's information
                    existingSupplier.setName(supplier.getName());
                    existingSupplier.setContactPerson(supplier.getContactPerson());
                    existingSupplier.setEmail(supplier.getEmail());
                    existingSupplier.setPhoneNumber(supplier.getPhoneNumber());
                    // Update other supplier properties as needed

                    // Save the updated supplier
                    supplierRepository.save(existingSupplier);
                } else {
                    // Handle the case where the supplier with the given ID is not found within the company
                }
            } else {
                // Logic for creating a new supplier within the company
                // Assuming company.addSupplier(supplier) is a method to add a supplier to the company
                company.getSuppliers().add(supplier);

                // Save the updated company
                companyRepository.save(company);
            }
            return company;
        }

        // Handle the case where the company with the given ID does not exist
        return null;
    }


    public String removeSupplier(Long companyId, Long supplierId) {
        // Find the company by its ID
        Company company = companyRepository.findById(companyId).orElse(null);

        if (company != null) {
            // Find the supplier by its ID
            Supplier supplier = supplierRepository.findById(supplierId).orElse(null);

            if (supplier != null) {
                // Remove the supplier from the company's list of suppliers
                company.getSuppliers().remove(supplier);

                // Save the updated company to persist the removal
                companyRepository.save(company);

                // Optionally, you can delete the supplier from the repository
                supplierRepository.deleteById(supplierId);

                return "Ok";
            } else {
                // Handle the case where the supplier with the given ID does not exist
                // You may choose to return an error message or handle it as needed
                return "Supplier not found";
            }
        } else {
            // Handle the case where the company with the given ID does not exist
            // You may choose to return an error message or handle it as needed
            return "Company not found";
        }
    }


    public Boolean updateStatus(long companyId, Boolean status) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company != null) {
            company.setStatus(status); // Assuming the 'Company' class has a 'setStatus' method

            companyRepository.save(company);
            return true;
        }
        return false;
    }

    public Boolean updateAddressStatus(long companyId, long addressId, Boolean status) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company != null) {
            for (CompanyAddress address : company.getCompanyAddresses()) {
                if (address.getId() == addressId) {
                    address.setStatus(status);
                    companyRepository.save(company);
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean updateEmployeeStatus(Long companyId, Long employeeId, Boolean status) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company == null) {
            throw new RuntimeException("Company not found with ID: " + companyId);
        }

        for (Employee employee : company.getEmployees()) {
            if (employee.getId().equals(employeeId)) {  // Use .equals() for object comparisons
                employee.setStatus(status);
                companyRepository.save(company);
                return true;
            }
        }

        throw new RuntimeException("Employee not found with ID: " + employeeId + " for company with ID: " + companyId);
    }

    public Page<Plant> searchPlantsByCompanyAndKeyword(Long companyId, String keyword, Pageable pageable) {
        return plantRepository.findByCompanyIdAndNameContainingIgnoreCase(companyId, keyword, pageable);
    }

    public Page<Plant> getPlantsByCompanyId(Long companyId, Pageable pageable) {
        return plantRepository.findByCompanyId(companyId, pageable);
    }


    public Company updateOrCreateCompanyAddress(Long companyId, CompanyAddress companyAddress) {
        // Fetch the company by its ID
        Company company = companyRepository.findById(companyId).orElse(null);

        // If the company doesn't exist, return null or handle as needed
        if (company == null) {
            return null;
        }

        // If the companyAddress has an ID, search for an existing address within the company
        if (companyAddress.getId() != null) {
            CompanyAddress existingAddress = company.getCompanyAddresses().stream()
                    .filter(addr -> addr.getId().equals(companyAddress.getId()))
                    .findFirst()
                    .orElse(null);

            // If an existing address is found, update its details
            if (existingAddress != null) {
                // Update standard address attributes
                existingAddress.setAddressLine1(companyAddress.getAddressLine1());
                existingAddress.setAddressLine2(companyAddress.getAddressLine2());
                existingAddress.setCity(companyAddress.getCity());
                existingAddress.setStateProvince(companyAddress.getStateProvince());
                existingAddress.setPostalCode(companyAddress.getPostalCode());
                existingAddress.setCountry(companyAddress.getCountry());
                existingAddress.setLocalizedAddress(companyAddress.getLocalizedAddress());

                // Update additional attributes
                existingAddress.setCounty(companyAddress.getCounty());
                existingAddress.setDistrict(companyAddress.getDistrict());
                existingAddress.setNeighborhood(companyAddress.getNeighborhood());
                existingAddress.setBuilding(companyAddress.getBuilding());
                existingAddress.setFloor(companyAddress.getFloor());
                existingAddress.setUnit(companyAddress.getUnit());
                existingAddress.setLandmark(companyAddress.getLandmark());
            } else {
                // If address with given ID isn't found, add it as a new address
                companyAddress.setCompany(company);  // Set the relationship
                company.getCompanyAddresses().add(companyAddress);
            }
        } else {
            // If companyAddress doesn't have an ID, add as a new address
            companyAddress.setCompany(company);  // Set the relationship
            company.getCompanyAddresses().add(companyAddress);
        }

        // Save the updated company
        companyRepository.save(company);

        return company;
    }

    public String removeAddress(Long companyId, Long addressId) {
        // Fetch the company by its ID
        Company company = companyRepository.findById(companyId).orElse(null);

        // If the company doesn't exist, return a failure message
        if (company == null) {
            return "Company not found";
        }

        // Find the address with the given ID within the company's addresses
        CompanyAddress addressToRemove = company.getCompanyAddresses().stream()
                .filter(addr -> addr.getId().equals(addressId))
                .findFirst()
                .orElse(null);

        // If the address is found, remove it
        if (addressToRemove != null) {
            company.getCompanyAddresses().remove(addressToRemove);

            // Since we are removing an entity, it's a good idea to use the repository
            // responsible for the address entity to ensure it gets removed from the database.

            companyAddressRepository.delete(addressToRemove);

            // Return success message
            return "Address removed successfully";
        } else {
            // Address with given ID not found, return failure message
            return "Address not found for the specified company";
        }
    }

    public String  updateOrAddBankDetails(Long companyId, BankDetails bankDetails) {
        // Fetch the company by its ID
        Company company = companyRepository.findById(companyId).orElse(null);

        // If the company doesn't exist, return null or handle as needed
        if (company == null) {
            return " Company Not found ";
        }

        // If the bankDetails has an ID, search for an existing bank detail within the company
        if (bankDetails.getId() != null) {
            BankDetails existingBankDetail = company.getBankAccounts().stream()
                    .filter(bank -> bank.getId().equals(bankDetails.getId()))
                    .findFirst()
                    .orElse(null);

            // If an existing bank detail is found, update its details
            if (existingBankDetail != null) {
                existingBankDetail.setAccountType(bankDetails.getAccountType());
                existingBankDetail.setAccountNumber(bankDetails.getAccountNumber());
                existingBankDetail.setBankName(bankDetails.getBankName());
                existingBankDetail.setIfsc(bankDetails.getIfsc());
                existingBankDetail.setPrimaryAccount(bankDetails.isPrimaryAccount());
                existingBankDetail.setStatus(bankDetails.getStatus());
            } else {
                // If bank detail with given ID isn't found, add it as a new detail
                bankDetails.setCompany(company);  // Set the relationship
                company.getBankAccounts().add(bankDetails);
            }
        } else {
            // If bankDetails doesn't have an ID, add as a new detail
            bankDetails.setCompany(company);  // Set the relationship
            company.getBankAccounts().add(bankDetails);
        }

        // Save the updated company
        companyRepository.save(company);

        return "Successs";
    }

    public String removeBankDetails(Long companyId, Long bankId) {
        // Fetch the company by its ID
        Company company = companyRepository.findById(companyId).orElse(null);

        // If the company doesn't exist, return a failure message
        if (company == null) {
            return "Company not found";
        }

        // Find the bank detail with the given ID within the company's bank details
        BankDetails bankDetailToRemove = company.getBankAccounts().stream()
                .filter(bank -> bank.getId().equals(bankId))
                .findFirst()
                .orElse(null);

        // If the bank detail is found, remove it
        if (bankDetailToRemove != null) {
            company.getBankAccounts().remove(bankDetailToRemove);

            // Since we are removing an entity, use the bankDetailsRepository
            bankDetailsRepository.delete(bankDetailToRemove);

            // Return success message
            return "Bank detail removed successfully";
        } else {
            // Bank detail with given ID not found, return failure message
            return "Bank detail not found for the specified company";
        }
    }

    public String updateOrcreateLocation(Long companyId, CompanyLocation location) {
        Company company = companyRepository.findById(companyId).orElse(null);


        if (company == null) {
            return "Company not found";
        }


        if (location.getId() != null) {
            CompanyLocation existingLocation = company.getLocations().stream()
                    .filter(loc -> loc.getId().equals(location.getId()))
                    .findFirst()
                    .orElse(null);


            if (existingLocation != null) {

                existingLocation.setLatitude(location.getLatitude());
                existingLocation.setLongitude(location.getLongitude());
                existingLocation.setIpAddress(location.getIpAddress());

            } else {
                // If location with the given ID isn't found, add it as a new location
                location.setCompany(company);
                company.getLocations().add(location);
            }
        } else {
            // If location doesn't have an ID, add it as a new location
            location.setCompany(company);
            company.getLocations().add(location);
        }

        companyRepository.save(company);
        return "Location updated or created successfully";
    }

    public String removeLocation(Long companyId, Long locationId) {
        Company company = companyRepository.findById(companyId).orElse(null);

        // Check if the company exists
        if (company == null) {
            return "Company not found";
        }

        CompanyLocation locationToRemove = company.getLocations().stream()
                .filter(loc -> loc.getId().equals(locationId))
                .findFirst()
                .orElse(null);

        // If the location is found, remove it
        if (locationToRemove != null) {
            company.getLocations().remove(locationToRemove);
            companyLocationRepository.delete(locationToRemove);
            return "Location removed successfully";
        } else {
            return "Location not found for the specified company";
        }
    }


}
