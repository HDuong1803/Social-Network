package com.example.demo.controller;

import com.example.demo.entity.Education;
import com.example.demo.entity.Experience;
import com.example.demo.entity.Profile;
import com.example.demo.entity.User;
import com.example.demo.request.AddEducation;
import com.example.demo.request.AddExperience;
import com.example.demo.request.CreateProfile;
import com.example.demo.service.ProfileService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/profiles")
    public ResponseEntity<?> createProfile(@RequestBody CreateProfile req, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Profile profile = profileService.createProfile(req, user.getId());

        return new ResponseEntity<ResponseMessage>(new ResponseMessage("User created successfully", profile),
                HttpStatus.OK);

    }

    @GetMapping(value = "/profiles/{userId}")
    public ResponseEntity<?> getProfileByUserId(@PathVariable(name = "userId") Long userId) {

        Profile profile = profileService.getByUser(userId);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage(profile),
                HttpStatus.OK);
    }

    @DeleteMapping(value = "/profiles/{userId}")
    public ResponseEntity<?> deleteProfileByUserId(@PathVariable(name = "userId") Long userId) {

        profileService.deleteProfile(userId);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Delete successfully"),
                HttpStatus.OK);
    }

    @PostMapping("/profiles/experience")
    public ResponseEntity<?> addExperience(@RequestBody AddExperience req, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Experience experience = profileService.addExperience(user.getId(), req);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Experience created successfully", experience),
                HttpStatus.OK);

    }

    @DeleteMapping(value = "/profiles/experience/{id}")
    public ResponseEntity<?> deleteExperience(@PathVariable(name = "id") Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        profileService.deleteExperience(user.getId(), id);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Delete successfully"),
                HttpStatus.OK);
    }

    @PostMapping("/profiles/education")
    public ResponseEntity<?> addEducation(@RequestBody AddEducation req, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Education education = profileService.addEducation(user.getId(), req);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Experience created successfully", education),
                HttpStatus.OK);

    }

    @DeleteMapping(value = "/profiles/education/{id}")
    public ResponseEntity<?> deleteEducation(@PathVariable(name = "id") Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        profileService.deleteEducation(user.getId(), id);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Delete successfully"),
                HttpStatus.OK);
    }

    @GetMapping(value = "/profiles/follow")
    public ResponseEntity<?> follow(@RequestParam(name = "fromUserId") Long fromUserId, @RequestParam(name = "toUserId") Long toUserId) {
       
        profileService.follow(fromUserId, toUserId);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("You have been follow this user"),
                HttpStatus.OK);
    }

    @GetMapping(value = "/profiles/unFollow")
    public ResponseEntity<?> unFollow(@RequestParam(name = "fromUserId") Long fromUserId, @RequestParam(name = "toUserId") Long toUserId) {
        profileService.unFollow(fromUserId, toUserId);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("You have been unfollow this user"),
                HttpStatus.OK);
    }

    @GetMapping(value = "/profiles/addFriend")
    public ResponseEntity<?> addFriend(@RequestParam(name = "fromUserId") Long fromUserId, @RequestParam(name = "toUserId") Long toUserId) {
       
        profileService.addFriend(fromUserId, toUserId);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("You have been added this user"),
                HttpStatus.OK);
    }

    @GetMapping(value = "/profiles/unFriend")
    public ResponseEntity<?> unFriend(@RequestParam(name = "fromUserId") Long fromUserId, @RequestParam(name = "toUserId") Long toUserId) {
       
        profileService.unFriend(fromUserId, toUserId);;
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("You have been unfriend this user"),
                HttpStatus.OK);
    }

    @GetMapping(value = "/profiles/acceptFriendRequest")
    public ResponseEntity<?> acceptFriendRequest(@RequestParam(name = "fromUserId") Long currentUserId, @RequestParam(name = "toUserId") Long requestUserId) {
       
        profileService.acceptFriendRequest(currentUserId, requestUserId);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("You have been accepted this user"),
                HttpStatus.OK);
    }




    

}
