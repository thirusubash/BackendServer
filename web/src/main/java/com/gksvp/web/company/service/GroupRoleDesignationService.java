package com.gksvp.web.company.service;

import com.gksvp.web.company.dto.CompanyGroupDTO;
import com.gksvp.web.company.dto.CompanyRoleDTO;
import com.gksvp.web.company.dto.DesignationDTO;
import com.gksvp.web.company.entity.Company;
import com.gksvp.web.company.entity.CompanyGroup;
import com.gksvp.web.company.entity.CompanyRole;
import com.gksvp.web.company.entity.Designation;
import com.gksvp.web.company.repository.CompanyGroupRepository;
import com.gksvp.web.company.repository.CompanyRoleRepository;
import com.gksvp.web.company.repository.DesignationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;


@Service
public class GroupRoleDesignationService {
    private final DesignationRepository designationRepository;
    private final CompanyRoleRepository companyRoleRepository;
    private final CompanyGroupRepository companyGroupRepository;
    private final ModelMapper modelMapper;

    public GroupRoleDesignationService(DesignationRepository designationRepository,
                                       CompanyRoleRepository companyRoleRepository,
                                       CompanyGroupRepository companyGroupRepository,
                                       ModelMapper modelMapper) {
        this.designationRepository = designationRepository;
        this.companyRoleRepository = companyRoleRepository;
        this.companyGroupRepository = companyGroupRepository;
        this.modelMapper = modelMapper;
    }

    // CRUD for Designation

    public DesignationDTO createDesignation(Designation designation) {
        Designation savedDesignation = designationRepository.save(designation);
        return modelMapper.map(savedDesignation, DesignationDTO.class);
    }

    public Page<CompanyRoleDTO> getAllDesignationsByCompanyId(Long companyId, Pageable pageable, String searchTerm) {
        Page<CompanyRole> companyRoles;

        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            companyRoles = companyRoleRepository.findByCompany_Id(companyId, pageable);
        } else {
            companyRoles = companyRoleRepository.findByCompany_IdAndNameContaining(companyId,  pageable, searchTerm);
        }

        return companyRoles.map(role -> modelMapper.map(role, CompanyRoleDTO.class));
    }



    public DesignationDTO updateDesignation(Designation designation) {
        Designation updatedDesignation = designationRepository.save(designation);
        return modelMapper.map(updatedDesignation, DesignationDTO.class);
    }

    public String deleteDesignation(Long companyId, Long designationId) {
        Designation designation = designationRepository.findById(designationId).orElse(null);

        if (designation == null) {
            return "No role found with role and company id";
        }

        if (designation.getDefaultDesignation()) { // Assuming this is a boolean method/property
            return "This cannot be deleted";
        }

        designationRepository.deleteById(designationId);
        return "Designation deleted successfully";
    }





    // CRUD for CompanyRole
    public Page<CompanyRoleDTO> getAllRolesByCompanyId(Long companyId, Pageable pageable, String searchTerm) {
        Page<CompanyRole> companyRoles;
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            companyRoles = companyRoleRepository.findByCompany_Id(companyId, pageable);
        } else {
            companyRoles = companyRoleRepository.findByCompanyIdAndNameContaining(companyId,  pageable,searchTerm);
        }
        return companyRoles.map(role -> modelMapper.map(role, CompanyRoleDTO.class));
    }

    public CompanyRoleDTO createRole(CompanyRole companyRole) {
        CompanyRole savedCompanyRole = companyRoleRepository.save(companyRole);
        return modelMapper.map(savedCompanyRole, CompanyRoleDTO.class);
    }

    public CompanyRoleDTO getRoleById(Long id) {
        CompanyRole companyRole = companyRoleRepository.findById(id).orElse(null);
        return companyRole != null ? modelMapper.map(companyRole, CompanyRoleDTO.class) : null;
    }

    public CompanyRoleDTO updateRole(CompanyRole companyRole) {
        CompanyRole updatedCompanyRole = companyRoleRepository.save(companyRole);
        return modelMapper.map(updatedCompanyRole, CompanyRoleDTO.class);
    }

    public void deleteRole(Long id, Long companyId) {
        companyRoleRepository.deleteById(id);
    }

    // CRUD for CompanyGroup
    public Page<CompanyGroupDTO> getAllGroupsByCompanyId(Long companyId, Pageable pageable, String searchTerm) {
        Page<CompanyGroup> companyGroups;
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            companyGroups = companyGroupRepository.findByCompany_Id(companyId, pageable);
        } else {
            companyGroups = companyGroupRepository.findByCompany_IdAndNameContaining(companyId, searchTerm, pageable);
        }
        return companyGroups.map(group -> modelMapper.map(group, CompanyGroupDTO.class));
    }

    // CRUD for CompanyGroup
    public CompanyGroupDTO createGroup(CompanyGroup companyGroup) {
        CompanyGroup savedCompanyGroup = companyGroupRepository.save(companyGroup);
        return modelMapper.map(savedCompanyGroup, CompanyGroupDTO.class);
    }

    public CompanyGroupDTO getGroupById(Long id) {
        CompanyGroup companyGroup = companyGroupRepository.findById(id).orElse(null);
        return companyGroup != null ? modelMapper.map(companyGroup, CompanyGroupDTO.class) : null;
    }

    public CompanyGroupDTO updateGroup(CompanyGroup companyGroup) {
        CompanyGroup updatedCompanyGroup = companyGroupRepository.save(companyGroup);
        return modelMapper.map(updatedCompanyGroup, CompanyGroupDTO.class);
    }

    public void deleteGroup(Long id, Long companyId) {
        companyGroupRepository.deleteById(id);
    }

    // CRUD for Designation
    public DesignationDTO updateDesignationForCompany(Long id, Long companyId, Designation designation) {
        if (designationRepository.existsByIdAndCompany_Id(id, companyId)) {
            designation.setId(id);
            Company company = new Company();
            company.setId(companyId);
            designation.setCompany(company);
            Designation savedDesignation = designationRepository.save(designation);
            return modelMapper.map(savedDesignation, DesignationDTO.class);
        }
        return null;
    }
    public DesignationDTO getDesignationByIdAndCompanyId(Long id, Long companyId) {
        Designation designation = designationRepository.findByIdAndCompany_Id(id, companyId);
        return designation != null ? modelMapper.map(designation, DesignationDTO.class) : null;
    }

    public DesignationDTO createDesignationForCompany(Long companyId, Designation designation) {
        Company company = new Company();
        company.setId(companyId);
        designation.setCompany(company);

        if (designation.getId() != null && designationRepository.existsByIdAndCompany_Id(designation.getId(), companyId)) {
            throw new IllegalStateException("Designation with this ID already exists for the given company!");
        }

        Designation savedDesignation = designationRepository.save(designation);
        return modelMapper.map(savedDesignation, DesignationDTO.class);
    }

    public DesignationDTO updateDesignationDefaultStatus(Long companyId, Long designationId, Boolean status) {
        // Find the designation by its ID and company ID
        Designation designation = designationRepository.findByIdAndCompany_Id(designationId, companyId);

        // Check if the designation exists
        if (designation == null) {
            throw new EntityNotFoundException("No Designation found with ID: " + designationId + " for Company ID: " + companyId);
        }

        // Update the default status
        designation.setDefaultDesignation(status);  // Assuming you have a setDefaultDesignation method in your entity

        // Save the updated entity
        Designation updatedDesignation = designationRepository.save(designation);

        // Convert entity to DTO and return
        return modelMapper.map(updatedDesignation, DesignationDTO.class);
    }



    public DesignationDTO updateDesignationStatus(Long companyId, Long designationId, Boolean status) {
        // Find the designation by its ID and company ID
        Optional<Designation> optionalDesignation = Optional.ofNullable(designationRepository.findByIdAndCompany_Id(designationId, companyId));

        // Check if the designation exists
        if (optionalDesignation.isEmpty()) {
            throw new EntityNotFoundException("No Designation found with ID: " + designationId + " for Company ID: " + companyId);
        }

        Designation designation = optionalDesignation.get();

        // Update the status
        designation.setStatus(status);  // Assuming you have a setStatus method in your entity

        // Save the updated entity
        Designation updatedDesignation = designationRepository.save(designation);

        // Convert entity to DTO and return
        return modelMapper.map(updatedDesignation, DesignationDTO.class);
    }

    public CompanyRoleDTO createRoleForCompany(Long companyId, CompanyRole role) {
        Company company = new Company();
        company.setId(companyId);
        role.setCompany(company);
        CompanyRole savedRole = companyRoleRepository.save(role);
        return modelMapper.map(savedRole, CompanyRoleDTO.class);
    }

    public CompanyRoleDTO getRoleByIdAndCompanyId(Long id, Long companyId) {
        Optional<CompanyRole> roleOptional = companyRoleRepository.findByIdAndCompany_Id(id, companyId);
        return roleOptional.map(role -> modelMapper.map(role, CompanyRoleDTO.class)).orElse(null);
    }

    public CompanyRoleDTO updateRoleForCompany(Long id, Long companyId, CompanyRole role) {
        if (companyRoleRepository.existsByIdAndCompany_Id(id, companyId)) {
            role.setId(id);
            Company company = new Company();
            company.setId(companyId);
            role.setCompany(company);
            CompanyRole savedRole = companyRoleRepository.save(role);
            return modelMapper.map(savedRole, CompanyRoleDTO.class);
        }
        return null;
    }
    public CompanyRoleDTO updateRoleDefaultStatus(Long companyId, Long roleId, Boolean status) {
        // Find the company role by its ID and company ID
        Optional<CompanyRole> optionalRole = companyRoleRepository.findByIdAndCompany_Id(roleId, companyId);

        // Check if the company role exists
        if (optionalRole.isEmpty()) {
            throw new EntityNotFoundException("No CompanyRole found with ID: " + roleId + " for Company ID: " + companyId);
        }

        CompanyRole role = optionalRole.get();

        // Update the default status
        role.setDefaultRole(status);  // Assuming you have a setDefaultRole method in your entity

        // Save the updated entity
        CompanyRole updatedRole = companyRoleRepository.save(role);

        // Convert entity to DTO and return
        return modelMapper.map(updatedRole, CompanyRoleDTO.class);
    }

    public CompanyRoleDTO updateRoleStatus(Long companyId, Long roleId, Boolean status) {
        // Find the company role by its ID and company ID
        Optional<CompanyRole> optionalRole = companyRoleRepository.findByIdAndCompany_Id(roleId, companyId);

        // Check if the company role exists
        if (optionalRole.isEmpty()) {
            throw new EntityNotFoundException("No CompanyRole found with ID: " + roleId + " for Company ID: " + companyId);
        }

        CompanyRole role = optionalRole.get();

        // Update the status
        role.setStatus(status);  // Assuming you have a setStatus method in your entity

        // Save the updated entity
        CompanyRole updatedRole = companyRoleRepository.save(role);

        // Convert entity to DTO and return
        return modelMapper.map(updatedRole, CompanyRoleDTO.class);
    }


    public CompanyGroupDTO createGroupForCompany(Long companyId, CompanyGroup group) {
        Company company = new Company();
        company.setId(companyId);
        group.setCompany(company);
        CompanyGroup savedGroup = companyGroupRepository.save(group);
        return modelMapper.map(savedGroup, CompanyGroupDTO.class);
    }

    public CompanyGroupDTO updateGroupForCompany(Long id, Long companyId, CompanyGroup group) {
        if (companyGroupRepository.existsByIdAndCompany_Id(id, companyId)) {
            group.setId(id);
            Company company = new Company();
            company.setId(companyId);
            group.setCompany(company);
            CompanyGroup savedGroup = companyGroupRepository.save(group);
            return modelMapper.map(savedGroup, CompanyGroupDTO.class);
        }
        return null;
    }




    public CompanyGroupDTO updateGroupDefaultStatus(Long companyId, Long groupId, Boolean status) {
        // Find the company group by its ID and company ID
        Optional<CompanyGroup> optionalGroup = companyGroupRepository.findByIdAndCompany_Id(groupId, companyId);

        // Check if the company group exists
        if (optionalGroup.isEmpty()) {
            throw new EntityNotFoundException("No CompanyGroup found with ID: " + groupId + " for Company ID: " + companyId);
        }

        CompanyGroup group = optionalGroup.get();

        // Update the default status
        group.setDefaultGroup(status);  // assuming you have a setDefaultStatus method in your entity

        // Save the updated entity
        CompanyGroup updatedGroup = companyGroupRepository.save(group);

        // Convert entity to DTO and return
        return modelMapper.map(updatedGroup, CompanyGroupDTO.class);
    }

    public CompanyGroupDTO updateGroupDefaultForUserStatus(Long companyId, Long groupId, Boolean status) {
        // Find the company group by its ID and company ID
        Optional<CompanyGroup> optionalGroup = companyGroupRepository.findByIdAndCompany_Id(groupId, companyId);

        // Check if the company group exists
        if (optionalGroup.isEmpty()) {
            throw new EntityNotFoundException("No CompanyGroup found with ID: " + groupId + " for Company ID: " + companyId);
        }

        CompanyGroup group = optionalGroup.get();

        // Update the default status
        group.setDefaultForUser(status);  // assuming you have a setDefaultStatus method in your entity

        // Save the updated entity
        CompanyGroup updatedGroup = companyGroupRepository.save(group);

        // Convert entity to DTO and return
        return modelMapper.map(updatedGroup, CompanyGroupDTO.class);
    }

    public CompanyGroupDTO updateGroupStatus(Long companyId, Long groupId, Boolean status) {
        // Find the company group by its ID and company ID
        Optional<CompanyGroup> optionalGroup = companyGroupRepository.findByIdAndCompany_Id(groupId, companyId);

        // Check if the company group exists
        if (optionalGroup.isEmpty()) {
            throw new EntityNotFoundException("No CompanyGroup found with ID: " + groupId + " for Company ID: " + companyId);
        }

        CompanyGroup group = optionalGroup.get();

        // Update the default status
        group.setStatus(status);  // assuming you have a setDefaultStatus method in your entity

        // Save the updated entity
        CompanyGroup updatedGroup = companyGroupRepository.save(group);

        // Convert entity to DTO and return
        return modelMapper.map(updatedGroup, CompanyGroupDTO.class);
    }
}
