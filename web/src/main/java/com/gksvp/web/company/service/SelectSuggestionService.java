package com.gksvp.web.company.service;

import com.gksvp.web.company.dto.DesignationDTO;
import com.gksvp.web.company.dto.*;
import com.gksvp.web.company.entity.*;
import com.gksvp.web.company.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SelectSuggestionService {
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final SupplierRepository supplierRepository;
    private final PlantRepository plantRepository;
    private final CompanyAddressRepository companyAddressRepository;
    private final BankDetailsRepository bankDetailsRepository;
    private final DesignationRepository designationRepository;
    private final CompanyLocationRepository companyLocationRepository;
    
    private final CompanyGroupRepository companyGroupRepository;
    private  final CompanyRoleRepository companyRoleRepository;
    private final ModelMapper modelMapper;  // <-- This line was fixed

    @Autowired
    public SelectSuggestionService(CompanyRepository companyRepository,
                                   EmployeeRepository employeeRepository,
                                   SupplierRepository supplierRepository,
                                   PlantRepository plantRepository,
                                   CompanyAddressRepository companyAddressRepository,
                                   BankDetailsRepository bankDetailsRepository,
                                   DesignationRepository designationRepository, CompanyLocationRepository companyLocationRepository,
                                   CompanyGroupRepository companyGroupRepository, CompanyRoleRepository companyRoleRepository, ModelMapper modelMapper) {   // <-- Added this parameter
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
        this.supplierRepository = supplierRepository;
        this.plantRepository = plantRepository;
        this.companyAddressRepository = companyAddressRepository;
        this.bankDetailsRepository = bankDetailsRepository;
        this.designationRepository = designationRepository;
        this.companyLocationRepository = companyLocationRepository;
        this.companyGroupRepository = companyGroupRepository;
        this.companyRoleRepository = companyRoleRepository;
        this.modelMapper = modelMapper; // <-- Set the field value
    }

    @Transactional(readOnly = true)
    public List<SelectPlantDTO> selectCompanyPlants(Long companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + companyId));
        List<Plant> plants = plantRepository.findByCompanyId(companyId);
        return plants.stream()
                .map(plant -> modelMapper.map(plant, SelectPlantDTO.class))
                .collect(Collectors.toList());
    }

    public List<SelectPlantDTO> searchPlantsByCompanyAndKeyword(Long companyId, String keyword) {
        List<Plant> plants = plantRepository.findByCompanyIdAndNameContainingIgnoreCase(companyId, keyword);
        return plants.stream()
                .map(plant -> modelMapper.map(plant, SelectPlantDTO.class))
                .collect(Collectors.toList());
    }

    public List<SelectCompanyDTO> selectCompany() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream()
                .map(company -> modelMapper.map(company, SelectCompanyDTO.class))
                .collect(Collectors.toList());
    }

    public List<SelectCompanyEmployeeDTO> selectCompanyEmployee(Long companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company == null) {
            return Collections.emptyList();
        }
        List<Employee> employees = company.getEmployees();
        return employees.stream()
                .map(employee -> modelMapper.map(employee, SelectCompanyEmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public List<SelectCompanyEmployeeDTO> searchEmployessByCompanyAndKeyword(Long companyId, String keyword) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company == null) {
            return Collections.emptyList();
        }
        return company.getEmployees().stream()
                .filter(employee -> employee.getFirstName().toLowerCase().contains(keyword.toLowerCase()))
                .map(employee -> modelMapper.map(employee, SelectCompanyEmployeeDTO.class))
                .collect(Collectors.toList());
    }


    public List<SelectCompanySuppliersDTO> selectCompanySuppliers(Long companyId){
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if(optionalCompany.isEmpty()) {
            return Collections.emptyList();
        }
        Company company = optionalCompany.get();
        List<Supplier> suppliers = company.getSuppliers();
        return suppliers.stream()
                .map(supplier -> modelMapper.map(supplier, SelectCompanySuppliersDTO.class))
                .collect(Collectors.toList());
    }

    public List<SelectSupplierDTO> selectSuppliers(){
        List<Supplier> suppliers = supplierRepository.findAll();
        return suppliers.stream()
                .map(supplier -> modelMapper.map(supplier, SelectSupplierDTO.class))
                .collect(Collectors.toList());
    }


    public List<DesignationDTO> selectDesignation(Long companyId) {
        // Fetch designations based on company id
        List<Designation> designations = designationRepository.findByCompany_Id(companyId);

        // Convert list of Designation to list of DesignationDTO using ModelMapper
        return designations.stream()
                .map(designation -> modelMapper.map(designation, DesignationDTO.class))
                .collect(Collectors.toList());
    }




    public List<SelectCompanyDesignationDTO> searchDesignationByCompanyAndKeyword(Long companyId, String keyword) {
        // Fetch the company using companyId
        Company company = companyRepository.findById(companyId).orElse(null);

        // Return an empty list if the company isn't found
        if (company == null) {
            return Collections.emptyList();
        }

        // Filter designations of the company based on the keyword and convert them to DTOs
        return company.getDesignations().stream()
                .filter(designation -> designation.getName().toLowerCase().contains(keyword.toLowerCase()))
                .map(designation -> modelMapper.map(designation, SelectCompanyDesignationDTO.class))
                .collect(Collectors.toList());
    }

    public List<SelectCompanyDesignationDTO> selectCompanyDesignation(Long companyId) {
        // Fetch the company using companyId
        Company company = companyRepository.findById(companyId).orElse(null);

        // Return an empty list if the company isn't found
        if (company == null) {
            return Collections.emptyList();
        }

        // Convert all designations of the company to DTOs
        return company.getDesignations().stream()
                .map(designation -> modelMapper.map(designation, SelectCompanyDesignationDTO.class))
                .collect(Collectors.toList());
    }

    public List<CompanyGroupDTO> searchGroupsByCompanyAndKeyword(Long companyId, String keyword) {
        if (companyId == null || keyword == null) {
            throw new IllegalArgumentException("Company ID and keyword cannot be null");
        }

        List<CompanyGroup> groups = companyGroupRepository.findByCompany_IdAndNameContaining(companyId, keyword);
        return groups.stream()
                .map(group -> modelMapper.map(group, CompanyGroupDTO.class))
                .collect(Collectors.toList());
    }

    public List<CompanyGroupDTO> selectCompanyGroups(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID cannot be null");
        }

        List<CompanyGroup> groups = companyGroupRepository.findByCompany_Id(companyId);
        return groups.stream()
                .map(group -> modelMapper.map(group, CompanyGroupDTO.class))
                .collect(Collectors.toList());
    }

    public List<CompanyRoleDTO> searchRolesByCompanyAndKeyword(Long companyId, String keyword) {
        if (companyId == null || keyword == null) {
            throw new IllegalArgumentException("Company ID and keyword cannot be null");
        }

        List<CompanyRole> roles = companyRoleRepository.findByCompany_IdAndNameContaining(companyId, keyword);
        return roles.stream()
                .map(role -> modelMapper.map(role, CompanyRoleDTO.class))
                .collect(Collectors.toList());
    }

    public List<CompanyRoleDTO> selectCompanyRoles(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID cannot be null");
        }

        List<CompanyRole> roles = companyRoleRepository.findByCompany_Id(companyId);
        return roles.stream()
                .map(role -> modelMapper.map(role, CompanyRoleDTO.class))
                .collect(Collectors.toList());
    }


}
