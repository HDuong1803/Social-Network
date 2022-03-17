package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Education;
import com.example.demo.entity.Experience;
import com.example.demo.entity.Friend;
import com.example.demo.entity.FriendRequest;
import com.example.demo.entity.Profile;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.EducationRepository;
import com.example.demo.repository.ExperienceRepository;
import com.example.demo.repository.FriendRepository;
import com.example.demo.repository.FriendRequestRepository;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.AddEducation;
import com.example.demo.request.AddExperience;
import com.example.demo.request.CreateProfile;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    public Profile createProfile(CreateProfile req, Long userId) {

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("Not found user");
        }

        Profile profile = Profile.builder()
                .website(req.getWebsite())
                .location(req.getLocation())
                .skills(req.getSkill())
                .socials(req.getSocial())
                .educations(req.getEducations())
                .experiences(req.getExperiences())
                .user(user.get())
                .build();

        return profileRepository.save(profile);

    }

    public void save(Profile profile) {
        profileRepository.save(profile);
    }

    public Profile getByUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("Not found user");
        }

        return profileRepository.findByUser(user.get());
    }

    public Profile findById(Long id) {

        return profileRepository.findById(id).get();
    }

    public void deleteProfile(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("Not found user");
        }
        Profile profile = profileRepository.findByUser(user.get());
        if (profile == null) {
            throw new NotFoundException("Not found profile");

        }

        profileRepository.deleteById(profile.getId());
    }

    public Experience addExperience(Long userId, AddExperience req) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).get());
        if (user.isPresent()) {
            Experience experience = Experience.builder()
                    .title(req.getTitle())
                    .companyName(req.getCompanyName())
                    .from(req.getFrom())
                    .to(req.getTo())
                    .location(req.getLocation())
                    .description(req.getDescription())
                    .build();
            experienceRepository.save(experience);
            Profile profile = profileRepository.findByUser(user.get());
            if (ObjectUtils.isEmpty(profile)) {
                throw new NotFoundException("Not found profile");
            } else {
                profile.getExperiences().add(experience);
                profileRepository.save(profile);
                return experience;
            }
        }

        return null;
    }

    public void deleteExperience(Long userId, Long experienceId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).get());
        if (user.isPresent()) {

            Profile profile = profileRepository.findByUser(user.get());
            if (ObjectUtils.isEmpty(profile)) {
                throw new NotFoundException("Not found profile");
            } else {

                profile.getExperiences().forEach(item -> {
                    if (item.getId() == experienceId) {
                        profile.getExperiences().remove(item);
                        experienceRepository.deleteById(item.getId());
                    }
                });
            }
        }
    }

    public Education addEducation(Long userId, AddEducation req) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).get());
        if (user.isPresent()) {
            Education education = Education.builder()
                    .schooleName(req.getSchooleName())
                    .fieldofstudy(req.getFieldofstudy())
                    .from(req.getFrom())
                    .to(req.getTo())
                    .description(req.getDescription())
                    .build();
            educationRepository.save(education);
            Profile profile = profileRepository.findByUser(user.get());
            if (ObjectUtils.isEmpty(profile)) {
                throw new NotFoundException("Not found profile");
            } else {
                profile.getEducations().add(education);
                profileRepository.save(profile);
                return education;
            }
        }

        return null;
    }

    public void deleteEducation(Long userId, Long educationId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).get());
        if (user.isPresent()) {

            Profile profile = profileRepository.findByUser(user.get());
            if (ObjectUtils.isEmpty(profile)) {
                throw new NotFoundException("Not found profile");
            } else {

                profile.getEducations().forEach(item -> {
                    if (item.getId() == educationId) {
                        profile.getEducations().remove(item);
                        educationRepository.deleteById(item.getId());
                    }
                });
            }
        }
    }

    public void follow(Long fromUserId, Long toUserId) {

     
        Profile fromProfile = findById(fromUserId);
        if (ObjectUtils.isEmpty(fromProfile)) {
            throw new NotFoundException("Not found profile for user");
        }

        Profile toProfile = findById(toUserId);
        if (ObjectUtils.isEmpty(toProfile)) {
            throw new NotFoundException("Not found profile for target user");
        }

        if (fromProfile.getFollowers().contains(toProfile)) {
            throw new BadRequestException("You has been already followed this user");
        }

        fromProfile.getFollowings().add(toProfile);
        toProfile.getFollowers().add(fromProfile);
        List<Profile> profiles = new ArrayList<>();
        profiles.add(fromProfile);
        profiles.add(toProfile);
        profileRepository.saveAll(profiles);

    }

    public void unFollow(Long fromUserId, Long toUserId) {

        Profile fromProfile = findById(fromUserId);
        if (ObjectUtils.isEmpty(fromProfile)) {
            throw new NotFoundException("Not found profile for user");
        }

        Profile toProfile = findById(toUserId);
        if (ObjectUtils.isEmpty(toProfile)) {
            throw new NotFoundException("Not found profile for target user");
        }

        if (!fromProfile.getFollowers().contains(toProfile)) {
            throw new BadRequestException("You has not being followed this user");
        }

        fromProfile.getFollowings().forEach(item -> {
            if (item.getId() == toUserId) {
                fromProfile.getFollowings().remove(item);
            }
        });

        toProfile.getFollowers().forEach(item -> {
            if (item.getId() == fromUserId) {
                toProfile.getFollowers().remove(item);
            }
        });

        List<Profile> profiles = new ArrayList<>();
        profiles.add(fromProfile);
        profiles.add(toProfile);
        profileRepository.saveAll(profiles);

    }

    public void addFriend(Long fromUserId, Long toUserId) {

        Profile fromProfile = findById(fromUserId);
        if (ObjectUtils.isEmpty(fromProfile)) {
            throw new NotFoundException("Not found profile for user");
        }

        Profile toProfile = findById(toUserId);
        if (ObjectUtils.isEmpty(toProfile)) {
            throw new NotFoundException("Not found profile for target user");
        }

        Friend toProfileFriend = friendRepository.findByProfile(toProfile);
        Friend fromProfileFriend = friendRepository.findByProfile(fromProfile);
        FriendRequest toProfileFriendRequest = friendRequestRepository.findByProfile(toProfile);
        FriendRequest fromProfileFriendRequest = friendRequestRepository.findByProfile(toProfile);

        if (fromProfile.getFriends().contains(toProfileFriend)
                && toProfile.getFriends().contains(fromProfileFriend)) {
            throw new BadRequestException("Target user has already been be friend by from user");
        } else if (fromProfile.getFriendsRequests().contains(toProfileFriendRequest)
                && toProfile.getFriendsRequests().contains(fromProfileFriendRequest)) {
            throw new BadRequestException("You has been already send a friend request to this user");
        } else {
            FriendRequest friendRequesToProfile = FriendRequest.builder()
                    .profile(toProfile)
                    .build();

            FriendRequest friendRequesFromProfile = FriendRequest.builder()
                    .profile(fromProfile)
                    .build();

            friendRequestRepository.save(friendRequesToProfile);
            friendRequestRepository.save(friendRequesFromProfile);

            fromProfile.getFriendsRequests().add(friendRequesToProfile);
            toProfile.getFriendsRequests().add(friendRequesFromProfile);

        }

        profileRepository.save(fromProfile);
        profileRepository.save(toProfile);

    }

    public void unFriend(Long fromUserId, Long toUserId) {

        Profile fromProfile = findById(fromUserId);
        if (ObjectUtils.isEmpty(fromProfile)) {
            throw new NotFoundException("Not found profile for user");
        }

        Profile toProfile = findById(toUserId);
        if (ObjectUtils.isEmpty(toProfile)) {
            throw new NotFoundException("Not found profile for target user");
        }

        Friend fromProfileFriend = friendRepository.findByProfile(fromProfile);

        if (!toProfile.getFriends().contains(fromProfileFriend)) {
            throw new BadRequestException("You has not yet be friend this user");
        }

        fromProfile.getFriends().forEach(item -> {
            if (item.getProfile().getId() == toUserId) {
                fromProfile.getFriends().remove(item);
            }
        });

        toProfile.getFriends().forEach(item -> {
            if (item.getProfile().getId() == fromUserId) {
                toProfile.getFriends().remove(item);
            }
        });

        List<Profile> profiles = new ArrayList<>();
        profiles.add(fromProfile);
        profiles.add(toProfile);
        profileRepository.saveAll(profiles);

    }

    public void acceptFriendRequest(Long currentUserId, Long requestUserId) {

        Profile currentProfile = findById(currentUserId);
        if (ObjectUtils.isEmpty(currentProfile)) {
            throw new NotFoundException("Not found profile for user");
        }

        Profile requestProfile = findById(requestUserId);
        if (ObjectUtils.isEmpty(requestProfile)) {
            throw new NotFoundException("Not found profile for target user");
        }

        currentProfile.getFriends().forEach(item -> {
            if (item.getProfile().equals(requestProfile)) {
                throw new BadRequestException("You has already been friend");

            }
        });

        requestProfile.getFriends().forEach(item -> {
            if (item.getProfile().equals(currentProfile)) {
                throw new BadRequestException("You has already been friend");

            }
        });

        FriendRequest requestProfileFriendRequest = friendRequestRepository.findByProfile(requestProfile);
        boolean inRequestFriend = false;

        currentProfile.getFriendsRequests().forEach(item -> {
            if (item.getProfile().equals(requestProfile)) {
                inRequestFriend = true;
                currentProfile.getFriendsRequests().remove(requestProfileFriendRequest);
            }
        });

        if (!inRequestFriend) {
            throw new BadRequestException("You has not any friend request related to this user");
        }

        Friend currentProfileFriend = friendRepository.findByProfile(currentProfile);
        Friend requestProfileFriend = friendRepository.findByProfile(requestProfile);

        currentProfile.getFriends().add(requestProfileFriend);
        requestProfile.getFriends().add(currentProfileFriend);

        List<Profile> profiles = new ArrayList<>();
        profiles.add(currentProfile);
        profiles.add(requestProfile);
        profileRepository.saveAll(profiles);

    }
}
