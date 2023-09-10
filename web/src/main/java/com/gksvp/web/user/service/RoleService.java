package com.gksvp.web.user.service;

import com.gksvp.web.user.entity.Role;
import com.gksvp.web.user.entity.User;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    Role getRoleById(Long id);

    Role getRoleByName(String name);

    Role createRole(Role role);

    Role updateRole(Long id, Role updatedRole);

    void deleteRole(Long id);

    List<User> getUsersInRole(Long id);

    List<User> updateUsersInRole(Long id, List<Long> userIds);

    List<User> removeUsersFromRole(Long id, List<Long> userIds);
}
