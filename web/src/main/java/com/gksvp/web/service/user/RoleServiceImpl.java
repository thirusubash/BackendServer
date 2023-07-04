package com.gksvp.web.service.user;

import com.gksvp.web.model.user.Role;
import com.gksvp.web.model.user.User;
import com.gksvp.web.repository.user.RoleRepository;
import com.gksvp.web.repository.user.UserRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Long id, Role updatedRole) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            role.setName(updatedRole.getName());
            role.setDescription(updatedRole.getDescription());
            // Update other attributes if necessary
            return roleRepository.save(role);
        } else {
            throw new IllegalArgumentException("Role not found with ID: " + id);
        }
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public List<User> getUsersInRole(Long id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            return role.getUsers();
        } else {
            throw new IllegalArgumentException("Role not found with ID: " + id);
        }
    }

    @Override
    public List<User> updateUsersInRole(Long id, List<Long> userIds) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            List<User> updatedUsers = new ArrayList<>();

            for (Long userId : userIds) {
                Optional<User> optionalUser = userRepository.findById(userId);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    user.removeRole(role); // Remove the role from the user
                    updatedUsers.add(user);
                }
            }

            roleRepository.save(role);
            return updatedUsers;
        } else {
            throw new IllegalArgumentException("Role not found with ID: " + id);
        }
    }

    @Override
    public List<User> removeUsersFromRole(Long id, List<Long> userIds) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            List<User> removedUsers = new ArrayList<>();

            for (Long userId : userIds) {
                Optional<User> optionalUser = userRepository.findById(userId);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    role.removeUser(user); // Remove the user from the role
                    removedUsers.add(user);
                }
            }

            roleRepository.save(role);
            return removedUsers;
        } else {
            throw new IllegalArgumentException("Role not found with ID: " + id);
        }
    }

}
