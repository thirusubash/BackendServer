package com.gksvp.web.user.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.management.relation.RoleNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gksvp.web.exception.GroupNotFoundException;
import com.gksvp.web.user.dto.KYCInfoDTO;
import com.gksvp.web.user.dto.UserDTO;
import com.gksvp.web.user.entity.Group;
import com.gksvp.web.user.entity.Role;
import com.gksvp.web.user.entity.User;
import com.gksvp.web.user.entity.UserKYCInfo;
import com.gksvp.web.user.repository.GroupRepository;
import com.gksvp.web.user.repository.RoleRepository;
import com.gksvp.web.user.repository.UserRepository;
import com.gksvp.web.user.service.UserService;
import com.gksvp.web.util.AESEncryption;

@Service
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AESEncryption aesEncryption;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
            RoleRepository roleRepository, GroupRepository groupRepository, AESEncryption aesEncryption) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.groupRepository = groupRepository;
        this.aesEncryption = aesEncryption;
    }

    // helper mthid for encrpt decypt
    private String encrypt(String plainText) throws Exception {
        return (plainText != null) ? aesEncryption.encrypt(plainText) : null;
    }

    private String decrypt(String cipherText) throws Exception {
        if (cipherText != null) {
            return aesEncryption.decrypt(cipherText);
        } else {
            throw new IllegalArgumentException("Invalid ciphertext length");
        }
    }

    // helper method to encrpt the userinformation
    private User encryptUser(User user) throws Exception {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setUserName(encrypt(user.getUserName()));
        user.setPassword(encodedPassword);
        user.setEmail(encrypt(user.getEmail()));
        user.setMobileNo(encrypt(user.getMobileNo()));
        user.setAlternateMobileNo(encrypt(user.getAlternateMobileNo()));
        return user;
    }

    private UserDTO mapUserToDTO(User user) throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setId(user.getId().toString());
        userDTO.setUserName(decrypt(user.getUserName()));
        userDTO.setMobileNo(decrypt(user.getMobileNo()));
        userDTO.setEmail(decrypt(user.getEmail()));
        userDTO.setActive(user.getActive());
        userDTO.setAddress(user.getAddress());

        // Map KYC information from UserKYCInfo to KYCInfoDTO
        UserKYCInfo kycInfo = user.getKycInfo();
        if (kycInfo != null) {
            KYCInfoDTO kycInfoDTO = new KYCInfoDTO();
            kycInfoDTO.setDocumentType(kycInfo.getDocumentType());
            kycInfoDTO.setDocumentNumber(kycInfo.getDocumentNumber());
            kycInfoDTO.setIssueDate(kycInfo.getIssueDate());
            kycInfoDTO.setExpiryDate(kycInfo.getExpiryDate());
            kycInfoDTO.setIssueAuthority(kycInfo.getIssueAuthority());
            // Set other KYC attributes

            userDTO.setKycInfoDto(kycInfoDTO);
        }
        return userDTO;
    }

    @Override
    public List<UserDTO> getAllUsers() throws Exception {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();

        try {
            for (User user : users) {
                userDTOs.add(mapUserToDTO(user));
            }
        } catch (Exception e) {
            throw e;
        }
        return userDTOs;
    }

    @Override
    public UserDTO getUserById(Long id) throws Exception {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserDTO userDTO = mapUserToDTO(user);
            return userDTO;
        } else {
            return null;
        }
    }

    @Override
    public User getUserByMobileNo(String mobileNo) throws Exception {
        return userRepository.findByMobileNo(aesEncryption.encrypt(mobileNo));
    }

    @Override
    public User getUserByEmail(String email) throws Exception {
        return userRepository.findByEmail(aesEncryption.encrypt(email));
    }

    @Override
    public User getUserByUserName(String userName) throws Exception {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User getUserByPAN(String PAN) throws Exception {
        return userRepository.findByEmail(aesEncryption.encrypt(PAN));
    }

    @Override
    public User getUserByAdhaar(String adhaarNo) throws Exception {
        return userRepository.findByEmail(aesEncryption.encrypt(adhaarNo));
    }

    @Override
    public User createUser(User user) throws RoleNotFoundException, GroupNotFoundException, Exception {
        user = encryptUser(user);

        // Set default role for new users
        Role defaultRole = roleRepository.findByName("user");
        if (defaultRole == null) {
            throw new RoleNotFoundException("Default role 'user' not found");
        }
        user.setRoles(new HashSet<>(Collections.singletonList(defaultRole)));

        // Set default group for new users
        Group defaultGroup = groupRepository.findByName("user");
        if (defaultGroup == null) {
            throw new GroupNotFoundException("Default group 'user' not found");
        }
        user.setGroups(new HashSet<>(Collections.singletonList(defaultGroup)));

        user.setActive(true);
        user = userRepository.save(user);
        user.setPassword("***************");

        return user;
    }

    @Override
    public User updateUser(Long id, User user) throws Exception {
        user = encryptUser(user);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setEmail(user.getEmail());
            existingUser.setMobileNo(user.getMobileNo());
            existingUser.setAlternateMobileNo(user.getAlternateMobileNo());
            existingUser.setCountryCode(user.getCountryCode());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setActive(user.getActive());
            existingUser.setLocation(user.getLocation());
            return userRepository.save(existingUser);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User updateGroupAndRoles(Long userId, Set<Long> groupIds, Set<Long> roleIds) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Update groups
            Set<Group> groups = new HashSet<>(groupRepository.findAllById(groupIds));
            user.setGroups(groups);
            // Update roles
            Set<Role> roles = new HashSet<>(roleRepository.findAllById(roleIds));
            user.setRoles(roles);
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }

    @Override
    public User createUserWithGroupsAndRoles(User user) throws Exception {
        // Encode the user's password before saving
        user = encryptUser(user);

        // Create sets to hold selected groups and roles
        Set<Group> selectedGroups = new LinkedHashSet<>();
        Set<Role> selectedRoles = new LinkedHashSet<>();

        // Fetch and add selected groups based on provided IDs
        for (Group group : user.getGroups()) {
            Optional<Group> groupOptional = groupRepository.findById(group.getId());
            groupOptional.ifPresent(selectedGroups::add);
        }

        // Fetch and add selected roles based on provided IDs
        for (Role role : user.getRoles()) {
            Optional<Role> roleOptional = roleRepository.findById(role.getId());
            roleOptional.ifPresent(selectedRoles::add);
        }

        // Save the user with encoded password
        User createdUser = userRepository.save(user);

        // Associate the user with selected groups
        for (Group group : selectedGroups) {
            group.addUser(createdUser);
        }

        // Assign selected roles to the user
        createdUser.setRoles(new HashSet<>(selectedRoles));

        // Save the user with updated groups and roles
        return userRepository.save(createdUser);
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
    public Boolean updateNumber(Long userId, String mobileNo) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setMobileNo(encrypt(mobileNo));
            User updatedUser = userRepository.save(user);
            return updatedUser.getMobileNo().equals(encrypt(mobileNo));
        } else {
            // Handle the case when the user with the given ID is not found
            return false;
        }
    }

    @Override
    public Boolean updateEmail(Long userId, String email) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmail(encrypt(email));
            User updatedUser = userRepository.save(user);
            return updatedUser.getEmail().equals(encrypt(email));
        } else {
            // Handle the case when the user with the given ID is not found
            return false;
        }
    }

}
