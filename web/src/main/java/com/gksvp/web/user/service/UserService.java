package com.gksvp.web.user.service;

import java.util.List;
import java.util.Set;

import com.gksvp.web.user.dto.UserDTO;
import com.gksvp.web.user.entity.User;

public interface UserService {

    List<UserDTO> getAllUsers() throws Exception;

    UserDTO getUserById(Long id) throws Exception;

    User createUser(User user) throws Exception;

    User updateUser(Long id, User user) throws Exception;

    boolean deleteUser(Long id) throws Exception;

    User getUserByMobileNo(String mobileNo) throws Exception;

    User getUserByEmail(String email) throws Exception;

    User getUserByUserName(String username) throws Exception;

    User getUserByPAN(String PAN) throws Exception;

    User getUserByAdhaar(String adhaarNo) throws Exception;

    User updateGroupAndRoles(Long userId, Set<Long> groupIds, Set<Long> roleIds) throws Exception;

    User createUserWithGroupsAndRoles(User user) throws Exception;

    Boolean updateNumber(Long userId, String mobileNo) throws Exception;

    public Boolean updateEmail(Long userId, String email) throws Exception;

    public boolean isEmailTaken(String email) throws Exception;

    public boolean isMobileTaken(String mobile) throws Exception;

    public boolean isUsernameTaken(String username) throws Exception;

}
