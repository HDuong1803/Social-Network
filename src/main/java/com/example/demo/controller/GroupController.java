package com.example.demo.controller;

import java.util.List;

import com.example.demo.entity.Group;
import com.example.demo.entity.Profile;
import com.example.demo.entity.User;
import com.example.demo.request.CreateGroup;
import com.example.demo.request.JoinGroup;
import com.example.demo.request.SetManager;
import com.example.demo.service.GroupService;
import com.example.demo.utils.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/groups")
    public ResponseEntity<?> createProfile(@RequestBody CreateGroup req, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Group group = groupService.createGroup(user.getId(), req);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage("User created successfully", group),
                HttpStatus.OK);

    }

    @GetMapping(value = "/groups")
    public ResponseEntity<?> findAllGroup() {

        List<Group> groups = groupService.findAllGroup();
        return new ResponseEntity<ResponseMessage>(new ResponseMessage(groups),
                HttpStatus.OK);
    }

    @GetMapping(value = "/groups/{groupId}/member")
    public ResponseEntity<?> getAllMembers(@PathVariable(name = "groupId") Long groupId) {

        List<Profile> profiles = groupService.getAllMembers(groupId);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage(profiles),
                HttpStatus.OK);
    }

    @DeleteMapping(value = "/groups/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable(name = "groupId") Long groupId) {
        groupService.deleteGroup(groupId);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Delete success"),
                HttpStatus.OK);
    }

    @PostMapping(value = "/groups/join")
    public ResponseEntity<?> joinGroup(@RequestBody JoinGroup req) {

        Group group = groupService.joinGroup(req);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Request success", group),
                HttpStatus.OK);
    }

    @PostMapping(value = "/groups/approve")
    public ResponseEntity<?> approveGroup(@RequestBody JoinGroup req) {

        Group group = groupService.approveGroup(req);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Approved", group),
                HttpStatus.OK);
    }

    @PostMapping(value = "/groups/addManager")
    public ResponseEntity<?> addManager(@RequestBody SetManager req) {

        Group group = groupService.addManager(req);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Request success", group),
                HttpStatus.OK);
    }

    @DeleteMapping(value = "/groups/removeManager")
    public ResponseEntity<?> removeManager(@RequestBody SetManager req) {

        Group group = groupService.removeManager(req);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Remove success", group),
                HttpStatus.OK);
    }

    @DeleteMapping(value = "/groups/removeMember")
    public ResponseEntity<?> removeMember(@RequestBody SetManager req) {

        Group group = groupService.removeMember(req);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Remove success", group),
                HttpStatus.OK);
    }



}
