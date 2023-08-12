package com.gksvp.web.user.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gksvp.web.user.entity.Group;
import com.gksvp.web.user.entity.Role;
import com.gksvp.web.user.entity.User;
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

    @Override
    public List<User> getAllUsers() throws Exception {
        List<User> users = userRepository.findAll();
        try {
            for (User user : users) {
                user.setUserName(aesEncryption.decrypt(user.getUserName()));
                user.setMobileNo(aesEncryption.decrypt(user.getMobileNo()));
                user.setEmail(aesEncryption.decrypt(user.getEmail()));
                user.setPassword("************");
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
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
    public User createUser(User user) throws Exception {

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setUserName(aesEncryption.encrypt(user.getUserName()));
        user.setPassword(encodedPassword);
        user.setEmail(aesEncryption.encrypt(user.getEmail()));
        user.setMobileNo(aesEncryption.encrypt(user.getMobileNo()));
        // set default role for new users
        Role defaultRole = roleRepository.findByName("user");
        user.setRoles(new HashSet<>(Collections.singletonList(defaultRole)));

        // set default group for new users
        Group defaultgroup = groupRepository.findByName("user");
        user.setGroups(new HashSet<>(Collections.singletonList(defaultgroup)));
        user.setActive(true);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setUserName(user.getUserName());
            existingUser.setEmail(user.getEmail());
            existingUser.setCountryCode(user.getCountryCode());
            existingUser.setMobileNo(user.getMobileNo());
            existingUser.setPassword(user.getPassword());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setActive(user.getActive());
            existingUser.setLocation(user.getLocation());

            // Update other fields as needed

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
    public User updateGroupAndRoles(Long userId, Set<Long> groupIds, Set<Long> roleIds) {
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
    public User createUserWithGroupsAndRoles(User user) {
        // Encode the user's password before saving
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

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
        User user = getUserById(userId);
        user.setMobileNo(mobileNo);
        User updateuser = userRepository.save(user);
        if (updateuser.getMobileNo() == mobileNo) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean updateEmail(Long userId, String email) throws Exception {
        User user = getUserById(userId);
        user.setEmail(email);
        User updateuser = userRepository.save(user);
        if (updateuser.getEmail() == email) {
            return true;
        } else {
            return false;
        }
    }

}
