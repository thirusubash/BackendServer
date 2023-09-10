package com.gksvp.web.user.service;

import java.util.List;
import java.util.Set;

import com.gksvp.web.user.entity.*;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    List<User> getAllUsers() throws Exception;

    User getUserById(Long id) throws Exception;

    User createUser(User user) throws Exception;

    User updateUser(Long id, User user) throws Exception;

    boolean deleteUser(Long id) throws Exception;

    User getUserByMobileNo(String mobileNo) throws Exception;

    User getUserByEmail(String email) throws Exception;

    User getUserByUserName(String username) throws Exception;

    User getUserByPAN(String PAN) throws Exception;

    User getUserByAdhaar(String AadhaarNo) throws Exception;

    User updateGroupAndRoles(Long userId, Set<Long> groupIds, Set<Long> roleIds) throws Exception;

    User createUserWithGroupsAndRoles(User user) throws Exception;

    Boolean updateNumber(Long userId, String mobileNo) throws Exception;

    Boolean updateEmail(Long userId, String email) throws Exception;

    boolean isEmailTaken(String email) throws Exception;

    boolean isMobileTaken(String mobile) throws Exception;

    boolean isUsernameTaken(String username) throws Exception;

    User addAddress(Long id, Address address) throws Exception;

    User removeAddress(Long id, Long addressId) throws Exception;

    User addOrUpdateUserToGroupsByName(Long id, Set<Group> groups) throws Exception;

    User removeGroupFromUser(Long id, List<Group> groups) throws Exception;

    User addRolesToUser(Long id, Set<Role> roles) throws Exception;

    User removeRolesFromUser(Long id, Set<Long> roleIds) throws Exception;

    User addKycInfo(Long userId,Set<UserKYCInfo> userKYCInfo) throws Exception;

    User addLocation(Long id, GeoLocation location) throws Exception;

    User updateKycStatus(Long id, Long kycInfoId, boolean status) throws Exception;
}
