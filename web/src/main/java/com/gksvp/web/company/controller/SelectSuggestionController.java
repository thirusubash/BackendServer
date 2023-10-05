package com.gksvp.web.company.controller;
import com.gksvp.web.company.dto.*;
import com.gksvp.web.company.service.SelectSuggestionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/suggest")
public class SelectSuggestionController {

    private final SelectSuggestionService selectSuggestionService;

    @Autowired
    public SelectSuggestionController(SelectSuggestionService selectSuggestionService) {
        this.selectSuggestionService = selectSuggestionService;
    }

    @GetMapping("/roles/{companyId}")
    public ResponseEntity<?> getCompanyRoles(
            @PathVariable Long companyId,
            @RequestParam(required = false) String keyword) {

        try {
            List<CompanyRoleDTO> roles;

            if (keyword != null && !keyword.trim().isEmpty()) {
                roles = selectSuggestionService.searchRolesByCompanyAndKeyword(companyId, keyword);
            } else {
                roles = selectSuggestionService.selectCompanyRoles(companyId);
            }

            return ResponseEntity.ok(roles);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
        }
    }
    @GetMapping("/groups/{companyId}")
    public ResponseEntity<?> getCompanyGroups(
            @PathVariable Long companyId,
            @RequestParam(required = false) String keyword) {

        try {
            List<CompanyGroupDTO> groups;

            if (keyword != null && !keyword.trim().isEmpty()) {
                groups = selectSuggestionService.searchGroupsByCompanyAndKeyword(companyId, keyword);
            } else {
                groups = selectSuggestionService.selectCompanyGroups(companyId);
            }

            return ResponseEntity.ok(groups);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
        }
    }
    @GetMapping("/plants/{companyId}")
    public ResponseEntity<?> getCompanyPlants(
            @PathVariable Long companyId,
            @RequestParam(required = false) String keyword) {

        try {
            List<SelectPlantDTO> selectPlantDTOs;

            if (keyword != null && !keyword.trim().isEmpty()) {
                selectPlantDTOs = selectSuggestionService.searchPlantsByCompanyAndKeyword(companyId, keyword);
            } else {
                selectPlantDTOs = selectSuggestionService.selectCompanyPlants(companyId);
            }

            return ResponseEntity.ok(selectPlantDTOs);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
        }
    }





    @GetMapping("/employees/{companyId}")
    public ResponseEntity<?> getCompanyEmployees(
            @PathVariable Long companyId,
            @RequestParam(required = false) String keyword) {
        try {
            List<SelectCompanyEmployeeDTO> employees;
            if (keyword != null && !keyword.trim().isEmpty()) {
                employees = selectSuggestionService.searchEmployessByCompanyAndKeyword(companyId, keyword);
            } else {
                employees = selectSuggestionService.selectCompanyEmployee(companyId);
            }
            return ResponseEntity.ok(employees);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "An error occurred while processing the request."));
        }
    }
    @GetMapping("/designations/{companyId}")
    public ResponseEntity<?> getCompanyDesignations(
            @PathVariable Long companyId,
            @RequestParam(required = false) String keyword) {
        try {
            List<SelectCompanyDesignationDTO> designations;
            if (keyword != null && !keyword.trim().isEmpty()) {
                designations = selectSuggestionService.searchDesignationByCompanyAndKeyword(companyId, keyword);
            } else {
                designations = selectSuggestionService.selectCompanyDesignation(companyId);
            }
            return ResponseEntity.ok(designations);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "An error occurred while processing the request."));
        }
    }



    @GetMapping("/suppliers/{companyId}")
    public ResponseEntity<List<SelectCompanySuppliersDTO>> getCompanySuppliers(@PathVariable Long companyId) {
        List<SelectCompanySuppliersDTO> suppliers = selectSuggestionService.selectCompanySuppliers(companyId);
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/suppliers")
    public ResponseEntity<List<SelectSupplierDTO>> getAllSuppliers() {
        List<SelectSupplierDTO> suppliers = selectSuggestionService.selectSuppliers();
        return ResponseEntity.ok(suppliers);
    }
    @GetMapping("/companies")
    public ResponseEntity<List<SelectCompanyDTO>> getCompanies() {
        List<SelectCompanyDTO> companies = selectSuggestionService.selectCompany();
        return ResponseEntity.ok(companies);
    }

}
