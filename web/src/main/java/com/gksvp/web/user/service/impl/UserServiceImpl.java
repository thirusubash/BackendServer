package com.gksvp.web.user.service.impl;

import com.gksvp.web.exception.GroupNotFoundException;
import com.gksvp.web.exception.UserNotFoundException;
import com.gksvp.web.user.dto.UserDto;
import com.gksvp.web.user.entity.*;
import com.gksvp.web.user.repository.GroupRepository;
import com.gksvp.web.user.repository.RoleRepository;
import com.gksvp.web.user.repository.UserRepository;
import com.gksvp.web.user.service.GroupService;
import com.gksvp.web.user.service.RoleService;
import com.gksvp.web.user.service.UserService;
import com.gksvp.web.util.AESEncryption;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AESEncryption aesEncryption;
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final GroupService groupService;
    private final GroupRepository groupRepository;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRepository userRepository, AESEncryption aesEncryption,
                           RoleRepository roleRepository, RoleService roleService,
                           GroupRepository groupRepository, GroupService groupService) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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
            user.setEmail(decrypt(user.getEmail()));
            user.setMobileNo(decrypt(user.getMobileNo()));
            // Mask the email address
            if (user.getEmail() != null && user.getEmail().length() >= 4) {
                int atIndex = user.getEmail().lastIndexOf('@');

                if (atIndex >= 0) {
                    String domain = user.getEmail().substring(atIndex);
                    String localPart = user.getEmail().substring(0, atIndex);

                    int localPartLength = localPart.length();
                    String maskedLocalPart = localPart.substring(0, 3) + // Show first 3 characters
                            new String(new char[localPartLength - 6]).replace('\0', '*') + // Mask middle characters
                            localPart.substring(localPartLength - 3); // Show last 3 characters

                    user.setEmail(maskedLocalPart + domain);
                }
            }

            // Mask the mobile number
            String mobileNo = user.getMobileNo();
            if (mobileNo != null && mobileNo.length() >= 4) {
                int maskedLength = mobileNo.length() - 4;
                char[] maskedChars = new char[maskedLength];
                Arrays.fill(maskedChars, '*');

                String maskedMobileNo = new String(maskedChars) + mobileNo.substring(maskedLength);
                user.setMobileNo(maskedMobileNo);
            }

            // Set a placeholder for the password
            user.setPassword("**** && ****");

            user.setLastName(decrypt(user.getLastName()));
            user.setFirstName(decrypt(user.getFirstName()));
            user.setUserName(decrypt(user.getUserName()));


            // Decrypt documentNumber in KycInfoList (if it exists)
            if (user.getKycInfoList() != null) {
                List<UserKYCInfo> kycInfoList = user.getKycInfoList();
                for (UserKYCInfo kycInfo : kycInfoList) {
                    if (kycInfo.getDocumentNumber() != null && kycInfo.getDocumentNumber().length() >= 4) {
                        String decryptedDocumentNumber = decrypt(kycInfo.getDocumentNumber());
                        char[] documentNumberChars = decryptedDocumentNumber.toCharArray();
                        int length = documentNumberChars.length;

                        // Mask all characters except the last 4
                        for (int i = 0; i < length - 4; i++) {
                            documentNumberChars[i] = '*';
                        }

                        kycInfo.setDocumentNumber(new String(documentNumberChars));
                    }
                }
            }
        }
    }



    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) throws Exception {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            processSensitiveUserData(user, false); // Decrypt sensitive user data
        }
        return modelMapper.map(user, UserDto.class); // Use 'user' as the source object
    }


    @Override
    public User createUser(User user) throws Exception {
        if (isEmailTaken(user.getEmail())) throw new Exception("Email is already taken");
        if (isMobileTaken(user.getMobileNo())) throw new Exception("Mobile number is already taken");
        if (isUsernameTaken(user.getUserName())) throw new Exception("Username is already taken");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
    public List<UserDto> getAllUsers() throws Exception {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            processSensitiveUserData(user, false); // Decrypt sensitive user data
        }
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }


    @Override
    public User updateUser(Long id, User user) throws Exception {
        processSensitiveUserData(user, true); // Encrypt sensitive user data

        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            // Update user fields based on your requirements
            if(!isEmailTaken(user.getEmail())){
                existingUser.setUserName(user.getUserName());
            }
            if(!isUsernameTaken(user.getUserName())){
                existingUser.setEmail(user.getEmail());
            }
           if(!isMobileTaken((user.getMobileNo()))){
               existingUser.setMobileNo(user.getMobileNo());
           }
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
        if (existingUser != null && !isMobileTaken(mobileNo)) {
            existingUser.setMobileNo(encrypt(mobileNo));
            return true;
        }
        return false;
    }


    @Override
    public Boolean updateEmail(Long userId, String email) throws Exception {
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null && isEmailTaken(email)) {
            existingUser.setEmail(encrypt(email));
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmailTaken(String email) throws Exception {
        return userRepository.existsByEmail(encrypt(email));
    }

    @Override
    public boolean isMobileTaken(String mobile) throws Exception {
        return userRepository.existsByMobileNo(encrypt(mobile));
    }

    @Override
    public boolean isUsernameTaken(String username) throws Exception {
        return userRepository.existsByUserName(encrypt(username));
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
        processSensitiveUserData(savedUser, false);
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
                    processSensitiveUserData(user , false);
                    return userRepository.save(user);
                }
            }
        }
        return null;
    }

}
