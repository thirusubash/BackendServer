package com.gksvp.web.user.service.impl;

import com.gksvp.web.exception.GroupNotFoundException;
import com.gksvp.web.exception.UserNotFoundException;
import com.gksvp.web.user.entity.*;
import com.gksvp.web.user.repository.*;
import com.gksvp.web.user.service.GroupService;
import com.gksvp.web.user.service.RoleService;
import com.gksvp.web.user.service.UserService;
import com.gksvp.web.util.AESEncryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final AESEncryption aesEncryption;
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final GroupService groupService;
    private final GroupRepository groupRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AESEncryption aesEncryption,
                           RoleRepository roleRepository, RoleService roleService,
                           GroupRepository groupRepository, GroupService groupService) {
        this.userRepository = userRepository;
        this.aesEncryption = aesEncryption;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.groupRepository = groupRepository;
        this.groupService = groupService;
    }

    private String encrypt(String string) throws Exception {
        return aesEncryption.encrypt(string);
    }

    private String decrypt(String string) throws Exception {
        return aesEncryption.decrypt(string);
    }

    private void processSensitiveUserData(User user, boolean encryptData) throws Exception {
        if (encryptData) {
            user.setLastName(encrypt(user.getLastName()));
            user.setFirstName(encrypt(user.getFirstName()));
            user.setUserName(encrypt(user.getUserName()));
            user.setEmail(encrypt(user.getEmail()));
            user.setMobileNo(encrypt(user.getMobileNo()));
        } else {
            user.setPassword("**** && ****");
            user.setLastName(decrypt(user.getLastName()));
            user.setFirstName(decrypt(user.getFirstName()));
            user.setUserName(decrypt(user.getUserName()));
            user.setEmail(decrypt(user.getEmail()));
            user.setMobileNo(decrypt(user.getMobileNo()));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) throws Exception {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            processSensitiveUserData(user, false); // Decrypt sensitive user data
        }
        return user;
    }

    @Override
    public User createUser(User user) throws Exception {
        processSensitiveUserData(user, true); // Encrypt sensitive user data

        // Assuming Role class has a constructor that takes the role ID
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByName("user"));
        user.setRoles(roles);

        // Assuming Group class has a constructor that takes the group ID
        Set<Group> groups = new HashSet<>();
        groups.add(groupService.getGroupByName("user"));
        user.setGroups(groups);

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() throws Exception {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            processSensitiveUserData(user, false); // Decrypt sensitive user data
        }
        return users;
    }

    @Override
    public User updateUser(Long id, User user) throws Exception {
        processSensitiveUserData(user, true); // Encrypt sensitive user data
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            // Update user fields based on your requirements
            existingUser.setUserName(user.getUserName());
            existingUser.setEmail(user.getEmail());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setDateOfBirth(user.getDateOfBirth());
            // Update other fields as needed

            // Save the updated user
            return userRepository.save(existingUser);
        }
        return null;
    }

    @Override
    public boolean deleteUser(Long id) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            userRepository.delete(existingUser);
            return true;
        }
        return false;
    }


    @Override
    @Transactional(readOnly = true)
    public User getUserByMobileNo(String mobileNo) {
        return userRepository.findByMobileNo(mobileNo);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByPAN(String PAN) throws Exception {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByAdhaar(String AadhaarNo) throws Exception {
        return null;
    }

    @Override
    public User updateGroupAndRoles(Long userId, Set<Long> groupIds, Set<Long> roleIds) {
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {

            return userRepository.save(existingUser);
        }
        return null;
    }

    @Override
    public User createUserWithGroupsAndRoles(User user) {
        return userRepository.save(user);
    }

    @Override
    public Boolean updateNumber(Long userId, String mobileNo) throws Exception {
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            existingUser.setMobileNo(encrypt(mobileNo));
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateEmail(Long userId, String email) throws Exception {
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            existingUser.setEmail(encrypt(email));
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean isMobileTaken(String mobile) {
        return userRepository.existsByMobileNo(mobile);
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUserName(username);
    }


    @Override
    @Transactional
    public User addAddress(Long userId, Address address) throws Exception {
        // Retrieve the user by their ID from the repository
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Set the user reference in the address
        address.setUser(user);

        // Add the address to the user's list of addresses
        user.getAddresses().add(address);

        // Save the user with the new address mapping
        return userRepository.save(user);
    }




    @Override
    @Transactional
    public User removeAddress(Long id, Long addressId) throws Exception {
        // Retrieve the user by their ID from the repository
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Find the address you want to remove from the user's list of addresses
            Address addressToRemove = user.getAddresses()
                    .stream()
                    .filter(address -> address.getId().equals(addressId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Address not found"));

            // Remove the address from the list
            user.getAddresses().remove(addressToRemove);

            // Save the user with the updated list of addresses
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    @Override
    public User addOrUpdateUserToGroupsByName(Long id, Set<Group> groups) throws Exception {
        // Find the user by userId
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Iterate through the list of group names
            for (Group inputGroup : groups) {
                // Find the group by groupName
                Optional<Group> groupOptional = Optional.ofNullable(groupRepository.findByName(inputGroup.getName()));

                if (groupOptional.isPresent()) {
                    Group existingGroup = groupOptional.get();

                    // Check if the user is already a member of the group
                    if (!user.getGroups().contains(existingGroup)) {
                        // If not, add the user to the group
                        user.getGroups().add(existingGroup);
                    }
                } else {
                    // Handle the case where the group with the given name was not found
                    throw new GroupNotFoundException("Group with name " + inputGroup.getName() + " not found.");
                }
            }

            // Save the updated user after adding to all groups
            userRepository.save(user);

            // Return the updated user
            return user;
        } else {
            // Handle the case where the user with the given ID was not found
            throw new UserNotFoundException("User with ID " + id + " not found.");
        }
    }






    @Override
    public User removeGroupFromUser(Long id, List<Group> groups) throws Exception {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            groups.forEach(user.getGroups()::remove);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    @Transactional
    public User addRolesToUser(Long id, Set<Role> roles) {
        return userRepository.findById(id)
                .map(user -> {
                    roles.forEach(role -> {
                        Role existingRole = roleRepository.findById(role.getId()).orElse(null);
                        if (existingRole != null) {
                            user.getRoles().add(existingRole);
                        } else {
                            logger.warn("Role with ID '{}' not found.", role.getId());
                        }
                    });
                    return userRepository.save(user);
                })
                .orElse(null);
    }

    @Override
    public User removeRolesFromUser(Long id, Set<Long> roleIds) throws Exception {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            List<Role> roles = roleRepository.findAllById(roleIds);
            roles.forEach(user.getRoles()::remove);
            return userRepository.save(user);
        }
        return null;
    }




    @Override
    @Transactional
    public User addKycInfo(Long userId, Set<UserKYCInfo> userKYCInfo) throws Exception {
        // Retrieve the user by their ID from the repository
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Loop through the provided UserKYCInfo instances and set the user for each one
        for (UserKYCInfo kycInfo : userKYCInfo) {
            // Encrypt the document number
            kycInfo.setDocumentNumber(encrypt(kycInfo.getDocumentNumber()));

            // Set the user
            kycInfo.setUser(user);
        }

        // Add the encrypted UserKYCInfo instances to the user's list of KYC information
        user.getKycInfoList().addAll(userKYCInfo);

        // Save the user with the updated list of KYC information
        User savedUser = userRepository.save(user);

        // Log a message indicating that KYC info has been added
        logger.warn("KYC info added for user with ID: {}", userId);

        return savedUser;
    }



    @Override
    @Transactional
    public User addLocation(Long id, GeoLocation location) throws Exception {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            location.setUser(user);
            user.getLocations().add(location);
            return userRepository.save(user);
        }
        return null;
    }




    @Override
    public User updateKycStatus(Long userId, Long kycInfoId, boolean status) throws Exception {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            List<UserKYCInfo> kycInfos = user.getKycInfoList();
            for (UserKYCInfo kycInfo : kycInfos) {
                if (kycInfo.getId().equals(kycInfoId)) {
                    kycInfo.setStatus(status);
                    return userRepository.save(user);
                }
            }
        }
        return null;
    }

}
