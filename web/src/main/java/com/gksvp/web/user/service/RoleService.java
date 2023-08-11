package com.gksvp.web.user.service;

import java.util.List;
import java.util.Optional;

import com.gksvp.web.user.entity.Role;
import com.gksvp.web.user.entity.User;

public interface RoleService {
    List<Role> getAllRoles();

    Optional<Role> getRoleById(Long id);

    Role createRole(Role role);

    Role updateRole(Long id, Role updatedRole);

    void deleteRole(Long id);

    List<User> getUsersInRole(Long id);

    List<User> updateUsersInRole(Long id, List<Long> userIds);

    List<User> removeUsersFromRole(Long id, List<Long> userIds);
}
