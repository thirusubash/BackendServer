package com.gksvp.web.user.controller;

import com.gksvp.web.user.entity.Group;
import com.gksvp.web.user.entity.User;
import com.gksvp.web.user.service.GroupService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable Long id) {
        Group group = groupService.getGroupById(id);
        if (group != null) {
            return new ResponseEntity<>(group, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        Group createdGroup = groupService.createGroup(group);
        return new ResponseEntity<>(createdGroup, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable Long id, @RequestBody Group updatedGroup) {
        Group group = groupService.updateGroup(id, updatedGroup);
        if (group != null) {
            return new ResponseEntity<>(group, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        boolean deleted = groupService.deleteGroup(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/users")
    public ResponseEntity<List<User>> addUsersToGroup(@PathVariable Long id, @RequestBody List<Long> userIds) {
        List<User> updatedUsers = groupService.updateUsersIntoGroup(id, userIds);
        if (updatedUsers != null) {
            return new ResponseEntity<>(updatedUsers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/users")
    public ResponseEntity<List<User>> removeUsersFromGroup(@PathVariable Long id, @RequestBody List<Long> userIds) {
        List<User> updatedUsers = groupService.removeUsersFromGroup(id, userIds);
        if (updatedUsers != null) {
            return new ResponseEntity<>(updatedUsers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<List<User>> getUsersInGroup(@PathVariable Long id) {
        List<User> users = groupService.listAllUsersInTheGroup(id);
        if (users != null) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
