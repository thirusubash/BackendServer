package com.gksvp.web.service.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gksvp.web.model.user.Group;
import com.gksvp.web.model.user.Role;
import com.gksvp.web.model.user.User;
import com.gksvp.web.repository.user.GroupRepository;
import com.gksvp.web.repository.user.RoleRepository;
import com.gksvp.web.repository.user.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
            RoleRepository roleRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    @Override
    public User getUserByMobileNo(String mobileNo) {
        return userRepository.findByMobileNo(mobileNo);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User getUserByPAN(String PAN) {
        return userRepository.findByPAN(PAN);
    }

    @Override
    public User getUserByAdhaar(String adhaarNo) {
        return userRepository.findByAdhaar(adhaarNo);
    }

    @Override
    public User createUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        // set default role for new users
        Role defaultRole = roleRepository.findByName("user");
        user.setRoles(new HashSet<>(Collections.singletonList(defaultRole)));

        // set default group for new users
        Group defaultgroup = groupRepository.findByName("user");
        user.setGroups(new HashSet<>(Collections.singletonList(defaultgroup)));

        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setUserName(user.getUserName());
            existingUser.setEmail(user.getEmail());
            existingUser.setCountrycode(user.getCountrycode());
            existingUser.setMobileNo(user.getMobileNo());
            existingUser.setPassword(user.getPassword());
            existingUser.setFirstname(user.getFirstname());
            existingUser.setLastname(user.getLastname());
            existingUser.setPAN(user.getPAN());
            existingUser.setAdhaar(user.getAdhaar());
            existingUser.setGstin(user.getGstin());
            existingUser.setPincode(user.getPincode());
            existingUser.setActive(user.getActive());
            existingUser.setHouseNo(user.getHouseNo());
            existingUser.setBuilding(user.getBuilding());
            existingUser.setStreet(user.getStreet());
            existingUser.setCity(user.getCity());
            existingUser.setState(user.getState());
            existingUser.setLandmark(user.getLandmark());
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
        // Create the user
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        List<Group> groups = new ArrayList<>();
        List<Role> roles = new ArrayList<>();

        // Get the groups based on the provided IDs
        for (Group group : user.getGroups()) {
            Optional<Group> groupOptional = groupRepository.findById(group.getId());
            groupOptional.ifPresent(groups::add);
        }

        // Get the roles based on the provided IDs
        for (Role role : user.getRoles()) {
            Optional<Role> roleOptional = roleRepository.findById(role.getId());
            roleOptional.ifPresent(roles::add);
        }

        User createdUser = userRepository.save(user);

        // Assign the user to the groups
        for (Group group : groups) {
            group.addUser(createdUser);
        }

        // Assign the user to the roles
        createdUser.setRoles(new HashSet<>(roles));

        // Save the user with updated groups and roles
        return userRepository.save(createdUser);
    }

}
