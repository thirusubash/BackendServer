package com.gksvp.web.user.service;

import java.util.List;
import java.util.Set;

import com.gksvp.web.user.entity.User;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User createUser(User user);

    User updateUser(Long id, User user);

    boolean deleteUser(Long id);

    User getUserByMobileNo(String mobileNo);

    User getUserByEmail(String email);

    User getUserByUserName(String username);

    User getUserByPAN(String PAN);

    User getUserByAdhaar(String adhaarNo);

    User updateGroupAndRoles(Long userId, Set<Long> groupIds, Set<Long> roleIds);

    User createUserWithGroupsAndRoles(User user);

}
