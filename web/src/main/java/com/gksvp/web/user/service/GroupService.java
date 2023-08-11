package com.gksvp.web.user.service;

import java.util.List;

import com.gksvp.web.user.entity.Group;
import com.gksvp.web.user.entity.User;

public interface GroupService {

    List<Group> getAllGroups();

    Group getGroupById(Long id);

    Group createGroup(Group group);

    Group updateGroup(Long id, Group updatedGroup);

    boolean deleteGroup(Long id);

    List<User> updateUsersIntoGroup(Long id, List<Long> userid);

    List<User> removeUsersFromGroup(Long id, List<Long> userid);

    List<User> listAllUsersInTheGroup(Long id);

}
