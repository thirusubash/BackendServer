package com.gksvp.web.user.controller;

import com.gksvp.web.user.dto.RoleDto;
import com.gksvp.web.user.dto.UserDto;
import com.gksvp.web.user.entity.Role;
import com.gksvp.web.user.entity.User;
import com.gksvp.web.user.service.RoleService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return roles.stream()
                .map(this::convertToDto) // Use a method to convert Role to RoleDto
                .toList();
    }

    private RoleDto convertToDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        // Map other properties from Role to RoleDto as needed
        return roleDto;
    }



    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id) {
        Role role = roleService.getRoleById(id);
       RoleDto roleDto= convertToDto(role);
        return ResponseEntity.ok(roleDto);
    }


    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    @PutMapping("/{id}")
    public Role updateRole(@PathVariable Long id, @RequestBody Role updatedRole) {
        return roleService.updateRole(id, updatedRole);
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }

    @GetMapping("/{id}/users")
    public List<User> getUsersInRole(@PathVariable Long id) {
        // Replace UserDto with the appropriate DTO class for your users
        return roleService.getUsersInRole(id);
    }

    @PutMapping("/{id}/users")
    public List<User> updateUserToRole(@PathVariable Long id, @RequestBody List<Long> userIds) {
        return roleService.updateUsersInRole(id, userIds);
    }

    @DeleteMapping("/{roleId}")
    public List<User> removeUsersFromRole(@PathVariable Long roleId, @RequestBody List<Long> userIds) {
        return roleService.removeUsersFromRole(roleId, userIds);
    }

}


