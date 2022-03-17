package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.demo.entity.Group;
import com.example.demo.entity.Manager;
import com.example.demo.entity.Member;
import com.example.demo.entity.MemberRequest;
import com.example.demo.entity.Profile;
import com.example.demo.entity.RoleManager;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.ManagerRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.MemberRequestRepository;
import com.example.demo.request.CreateGroup;
import com.example.demo.request.JoinGroup;
import com.example.demo.request.SetManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private MemberRequestRepository memberRequestRepository;

    public Group createGroup(Long userId, CreateGroup req) {
        Profile profile = profileService.getByUser(userId);
        if (ObjectUtils.isEmpty(profile)) {
            throw new NotFoundException("User id is not exist");
        }

        Group group = groupRepository.findByNameAndCode(req.getName(), req.getCode());
        if (!ObjectUtils.isEmpty(group)) {
            throw new BadRequestException("Name or code existedage");
        }

        group = Group.builder()
                .name(req.getName())
                .description(req.getDescription())
                .code(req.getCode())
                .date(new Date())
                .creator(profile)
                .build();

        return groupRepository.save(group);

    }

    public List<Group> findAllGroup() {
        return (List<Group>) groupRepository.findAll();
    }

    public List<Profile> getAllMembers(Long groupId) {
        Group group = groupRepository.findById(groupId).get();
        if (ObjectUtils.isEmpty(group)) {
            throw new NotFoundException("Group id is not exist");

        }

        List<Profile> responses = new ArrayList<>();

        group.getMembers().forEach(item -> {
            responses.add(item.getProfile());

        });

        return responses;

    }

    public Group updateGroup(Long groupId, CreateGroup req) {
        Group existingGroup = groupRepository.findById(groupId).get();
        if (ObjectUtils.isEmpty(existingGroup)) {
            throw new NotFoundException("Group id is not exist");
        }

        Group group = groupRepository.findByNameAndCode(req.getName(), req.getCode());
        if (!ObjectUtils.isEmpty(group)) {
            throw new BadRequestException("Name or code existe");
        }

        existingGroup.setName(req.getName());
        existingGroup.setCode(req.getCode());
        existingGroup.setDescription(req.getDescription());
        existingGroup.setUpdateDate(new Date());

        return groupRepository.save(group);

    }

    public void deleteGroup(Long groupId) {
        Group existingGroup = groupRepository.findById(groupId).get();
        if (ObjectUtils.isEmpty(existingGroup)) {
            throw new NotFoundException("Group id is not exist");
        }

        groupRepository.deleteById(groupId);

    }

    public Group joinGroup(JoinGroup req) {
        Group group = groupRepository.findById(req.getGroupId()).get();
        if (ObjectUtils.isEmpty(group)) {
            throw new NotFoundException("Group id is not exist");
        }

        Profile profile = profileService.findById(req.getProfileId());
        if (ObjectUtils.isEmpty(profile)) {
            throw new NotFoundException("User id is not exist");
        }

        group.getMemberRequests().forEach(item -> {
            if (item.getProfile().equals(profile)) {
                throw new BadRequestException("You has already been requested to join this group");
            }
        });

        group.getMembers().forEach(item -> {
            if (item.getProfile().equals(profile)) {
                throw new BadRequestException("You has already been be member of this group");
            }
        });

        MemberRequest memberRequest = MemberRequest.builder()
                .date(new Date())
                .profile(profile)
                .build();
        memberRequestRepository.save(memberRequest);

        group.getMemberRequests().add(memberRequest);

        return groupRepository.save(group);

    }

    public Group approveGroup(JoinGroup req) {
        Group group = groupRepository.findById(req.getGroupId()).get();
        if (ObjectUtils.isEmpty(group)) {
            throw new NotFoundException("Group id is not exist");
        }

        Profile profile = profileService.findById(req.getProfileId());
        if (ObjectUtils.isEmpty(profile)) {
            throw new NotFoundException("User id is not exist");
        }

        group.getMemberRequests().forEach(item -> {
            if (!item.getProfile().equals(profile)) {
                throw new BadRequestException("There is not any request of this user");
            } else {
                group.getMemberRequests().remove(item);
            }
        });

        group.getMembers().forEach(item -> {
            if (!item.getProfile().equals(profile)) {
                throw new BadRequestException("You has already been be member of this group");
            } else {
                Member member = Member.builder()
                        .date(new Date())
                        .profile(profile)
                        .build();
                memberRepository.save(member);
                group.getMembers().add(member);
            }
        });

        return groupRepository.save(group);

    }

    public Group addManager(SetManager req) {
        Group group = groupRepository.findById(req.getGroupId()).get();
        if (ObjectUtils.isEmpty(group)) {
            throw new NotFoundException("Group id is not exist");
        }

        Profile profile = profileService.findById(req.getProfileId());
        if (ObjectUtils.isEmpty(profile)) {
            throw new NotFoundException("User id is not exist");
        }

        group.getManagers().forEach(item -> {
            if (item.getProfile().equals(profile)) {
                throw new BadRequestException("You has already been set manger to this group");
            }
        });

        Manager manager = Manager.builder()
                .profile(profile)
                .date(new Date())
                .role(RoleManager.valueOf(req.getRole()))
                .build();
        managerRepository.save(manager);

        group.getManagers().add(manager);

        return groupRepository.save(group);

    }

    public Group removeMember(SetManager req) {

        Group group = groupRepository.findById(req.getGroupId()).get();
        if (ObjectUtils.isEmpty(group)) {
            throw new NotFoundException("Group id is not exist");
        }

        Profile profile = profileService.findById(req.getProfileId());
        if (ObjectUtils.isEmpty(profile)) {
            throw new NotFoundException("User id is not exist");
        }

        Member member = memberRepository.findByProfile(profile);

        if (!group.getMembers().contains(member)) {
            throw new BadRequestException("You has not yet been member of this group");
        } else if (group.getMembers().size() == 1) {
            throw new BadRequestException("You are last member of this group. Cannot delete");

        } else {

            group.getMembers().remove(member);
        }

        return groupRepository.save(group);

    }

    public Group removeManager(SetManager req) {

        Group group = groupRepository.findById(req.getGroupId()).get();
        if (ObjectUtils.isEmpty(group)) {
            throw new NotFoundException("Group id is not exist");
        }

        Profile profile = profileService.findById(req.getProfileId());
        if (ObjectUtils.isEmpty(profile)) {
            throw new NotFoundException("User id is not exist");
        }

        Manager manager = managerRepository.findByProfile(profile);

        if (!group.getManagers().contains(manager)) {
            throw new BadRequestException("You has not yet been manager of this group");
        } else {

            group.getManagers().remove(manager);
        }

        return groupRepository.save(group);

    }

}
