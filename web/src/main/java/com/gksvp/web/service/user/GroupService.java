package com.gksvp.web.service.user;

import com.gksvp.web.model.user.Group;
import com.gksvp.web.model.user.User;

import java.util.List;

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
