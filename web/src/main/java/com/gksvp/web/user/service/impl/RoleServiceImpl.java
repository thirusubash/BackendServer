package com.gksvp.web.user.service.impl;

import com.gksvp.web.user.dto.RoleDto;
import com.gksvp.web.user.entity.Role;
import com.gksvp.web.user.entity.User;
import com.gksvp.web.user.repository.RoleRepository;
import com.gksvp.web.user.repository.UserRepository;
import com.gksvp.web.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }




    private RoleDto convertToDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        // Set other properties as needed
        return roleDto;
}

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }


    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Long id, Role updatedRole) {
        Optional<Role> existingRole = roleRepository.findById(id);
        if (existingRole.isPresent()) {
            Role roleToUpdate = existingRole.get();
            roleToUpdate.setName(updatedRole.getName());
            roleToUpdate.setDescription(updatedRole.getDescription());
            return roleRepository.save(roleToUpdate);
        }
        return null;
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsersInRole(Long id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            return new ArrayList<>(role.getUsers());
        }
        return new ArrayList<>(); // Return an empty list if the role is not found or has no users.
    }

    @Override
    public List<User> updateUsersInRole(Long id, List<Long> userIds) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            List<User> users = role.getUsers();
            List<User> updatedUsers = new ArrayList<>();

            for (Long userId : userIds) {
                Optional<User> userOptional = userRepository.findById(userId);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    if (!users.contains(user)) {
                        users.add(user);
                        updatedUsers.add(user);
                    }
                } else {
                    // You might want to log or handle the case where a user doesn't exist.
                    // Depending on your use case, you could skip or handle it differently.
                    // For now, we'll skip it and continue adding other users.
                    continue;
                }
            }

            // Save the updated role to reflect the changes in the database.
            roleRepository.save(role);

            return updatedUsers;
        }
        return null; // Return null if the role is not found.
    }


    @Override
    public List<User> removeUsersFromRole(Long roleId, List<Long> userIds) {
        // Find the role by ID
        Optional<Role> roleOptional = roleRepository.findById(roleId);

        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            List<User> users = role.getUsers();
            List<User> updatedUsers = new ArrayList<>();

            for (Long userId : userIds) {
                // Find the user by ID
                Optional<User> userOptional = userRepository.findById(userId);

                if (userOptional.isPresent()) {
                    User user = userOptional.get();

                    // Remove the user from the role
                    if (users.remove(user)) {
                        updatedUsers.add(user);
                    } else {
                        // Handle the case where the user is not in the role
                        // You can throw an exception or handle it as needed.
                    }
                } else {
                    // Handle the case where the user with the given userId doesn't exist
                    // You can throw an exception or handle it as needed.
                }
            }

            // Save the updated role
            roleRepository.save(role);
            return updatedUsers;
        }

        return Collections.emptyList(); // Return an empty list if the role is not found.
    }

}
