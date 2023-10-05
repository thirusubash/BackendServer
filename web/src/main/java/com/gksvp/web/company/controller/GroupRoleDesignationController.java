package com.gksvp.web.company.controller;

import com.gksvp.web.company.dto.CompanyGroupDTO;
import com.gksvp.web.company.dto.CompanyRoleDTO;
import com.gksvp.web.company.dto.DesignationDTO;
import com.gksvp.web.company.entity.CompanyGroup;
import com.gksvp.web.company.entity.CompanyRole;
import com.gksvp.web.company.entity.Designation;
import com.gksvp.web.company.service.GroupRoleDesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/company/{companyId}")
public class GroupRoleDesignationController {

    @Autowired
    private GroupRoleDesignationService service;

    // CRUD Endpoints for Designation
    @GetMapping("/designations")
    public ResponseEntity<Page<CompanyRoleDTO>> getAllDesignation(
            @PathVariable Long companyId,
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) String searchTerm) {

        Page<CompanyRoleDTO> roles = service.getAllDesignationsByCompanyId(companyId, pageable, searchTerm);
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/designations/{id}")
    public ResponseEntity<DesignationDTO> getDesignationById(@PathVariable Long companyId, @PathVariable Long id) {
        return ResponseEntity.ok(service.getDesignationByIdAndCompanyId(id, companyId));
    }


    @PostMapping("/designations")
    public ResponseEntity<DesignationDTO> createDesignation(@PathVariable Long companyId, @RequestBody Designation designation) {
        return ResponseEntity.ok(service.createDesignationForCompany(companyId, designation));
    }

    @DeleteMapping("/designations/{designationId}")
    public ResponseEntity<?> deleteDesignation(@PathVariable Long companyId, @PathVariable Long designationId) {
        service.deleteDesignation(companyId, designationId);
        return ResponseEntity.noContent().build(); // return 204 No Content
    }


    @PutMapping("/designations/{id}")
    public ResponseEntity<DesignationDTO> updateDesignation(@PathVariable Long companyId, @PathVariable Long id, @RequestBody Designation designation) {
        return ResponseEntity.ok(service.updateDesignationForCompany(id, companyId, designation));
    }


    @PatchMapping("/designations/{id}/default")
    public ResponseEntity<CompanyRoleDTO> updateDesignationsStatus( @PathVariable Long companyId,
                                                            @PathVariable(name = "id") Long groupId,
                                                            @RequestParam Boolean status) {
        return ResponseEntity.ok(service.updateRoleDefaultStatus(companyId, groupId, status));
    }

    @PatchMapping("/designations/{id}")
    public ResponseEntity<CompanyRoleDTO> updateDesignationsDefaultStatus( @PathVariable Long companyId,
                                                                   @PathVariable(name = "id") Long groupId,
                                                                   @RequestParam Boolean status) {
        return ResponseEntity.ok(service.updateRoleStatus(companyId, groupId, status));
    }

    // CRUD Endpoints for CompanyRole
    @GetMapping("/roles")
    public ResponseEntity<Page<CompanyRoleDTO>> getAllRoles(
            @PathVariable Long companyId,
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) String searchTerm) {

        Page<CompanyRoleDTO> roles = service.getAllRolesByCompanyId(companyId, pageable, searchTerm);
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/roles")
    public ResponseEntity<CompanyRoleDTO> createRole(@PathVariable Long companyId, @RequestBody CompanyRole role) {
        return ResponseEntity.ok(service.createRoleForCompany(companyId, role));
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<CompanyRoleDTO> getRoleById(@PathVariable Long companyId, @PathVariable Long id) {
        return ResponseEntity.ok(service.getRoleByIdAndCompanyId(id, companyId));
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<CompanyRoleDTO> updateRole(@PathVariable Long companyId, @PathVariable Long id, @RequestBody CompanyRole role) {
        return ResponseEntity.ok(service.updateRoleForCompany(id, companyId, role));
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long companyId, @PathVariable Long id) {
        service.deleteRole(id, companyId);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/roles/{id}/default")
    public ResponseEntity<CompanyRoleDTO> updateRoleStatus( @PathVariable Long companyId,
                                                              @PathVariable(name = "id") Long groupId,
                                                              @RequestParam Boolean status) {
        return ResponseEntity.ok(service.updateRoleDefaultStatus(companyId, groupId, status));
    }

    @PatchMapping("/roles/{id}")
    public ResponseEntity<CompanyRoleDTO> updateRoleDefaultStatus( @PathVariable Long companyId,
                                                                     @PathVariable(name = "id") Long groupId,
                                                                     @RequestParam Boolean status) {
        return ResponseEntity.ok(service.updateRoleStatus(companyId, groupId, status));
    }


    // CRUD Endpoints for CompanyGroup
    @GetMapping("/groups")
    public ResponseEntity<Page<CompanyGroupDTO>> getAllCompanyGroups(
            @PathVariable Long companyId,
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) String searchTerm) {

        Page<CompanyGroupDTO> groups = service.getAllGroupsByCompanyId(companyId, pageable, searchTerm);
        return ResponseEntity.ok(groups);
    }


    @PostMapping("/groups")
    public ResponseEntity<CompanyGroupDTO> createGroup(@PathVariable Long companyId, @RequestBody CompanyGroup group) {
        return ResponseEntity.ok(service.createGroupForCompany(companyId, group));
    }

    @GetMapping("/groups/{id}")
    public ResponseEntity<CompanyGroupDTO> getGroupById(@PathVariable Long companyId, @PathVariable Long id) {
        return ResponseEntity.ok(service.getGroupById(companyId));
    }

    @PutMapping("/groups/{id}")
    public ResponseEntity<CompanyGroupDTO> updateGroup(@PathVariable Long companyId, @PathVariable Long id, @RequestBody CompanyGroup group) {
        return ResponseEntity.ok(service.updateGroupForCompany(id, companyId, group));
    }

    @PatchMapping("/groups/{id}")
    public ResponseEntity<CompanyGroupDTO> updateGroupStatus( @PathVariable Long companyId,
                                                                     @PathVariable(name = "id") Long groupId,
                                                                     @RequestParam Boolean status) {
        return ResponseEntity.ok(service.updateGroupStatus(companyId, groupId, status));
    }

    @PatchMapping("/groups/{id}/default")
    public ResponseEntity<CompanyGroupDTO> updateGroupDefaultStatus( @PathVariable Long companyId,
                                                              @PathVariable(name = "id") Long groupId,
                                                              @RequestParam Boolean status) {
        return ResponseEntity.ok(service.updateGroupDefaultStatus(companyId, groupId, status));
    }

    @PatchMapping("/groups/{id}/user-default")
    public ResponseEntity<CompanyGroupDTO> updateGroupDefaultForUserStatus( @PathVariable Long companyId,
                                                                     @PathVariable(name = "id") Long groupId,
                                                                     @RequestParam Boolean status) {
        return ResponseEntity.ok(service.updateGroupDefaultForUserStatus(companyId, groupId, status));
    }


    @DeleteMapping("/groups/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long companyId, @PathVariable Long id) {
        service.deleteGroup(id, companyId);
        return ResponseEntity.noContent().build();
    }

}
