package com.gksvp.web.service.user;

import com.gksvp.web.model.user.Group;
import com.gksvp.web.model.user.User;
import com.gksvp.web.repository.user.GroupRepository;
import com.gksvp.web.repository.user.UserRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Group getGroupById(Long id) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        return optionalGroup.orElse(null);
    }

    @Override
    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group updateGroup(Long id, Group updatedGroup) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            group.setName(updatedGroup.getName());
            return groupRepository.save(group);
        }
        return null;
    }

    @Override
    public boolean deleteGroup(Long id) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            groupRepository.delete(optionalGroup.get());
            return true;
        }
        return false;
    }

    @Override
    public List<User> updateUsersIntoGroup(Long id, List<Long> userIds) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            List<User> users = group.getUsers();
            List<User> updatedUsers = new ArrayList<>();
            for (Long userId : userIds) {
                Optional<User> optionalUser = userRepository.findById(userId);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    users.add(user);
                    updatedUsers.add(user);
                }
            }
            groupRepository.save(group);
            return updatedUsers;
        }
        return null;
    }

    @Override
    public List<User> removeUsersFromGroup(Long id, List<Long> userIds) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            List<User> users = group.getUsers();
            List<User> updatedUsers = new ArrayList<>();
            for (Long userId : userIds) {
                Optional<User> optionalUser = userRepository.findById(userId);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    users.remove(user);
                    updatedUsers.add(user);
                }
            }
            groupRepository.save(group);
            return updatedUsers;
        }
        return null;
    }

    @Override
    public List<User> listAllUsersInTheGroup(Long id) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            return new ArrayList<>(group.getUsers());
        }
        return null;
    }
}
