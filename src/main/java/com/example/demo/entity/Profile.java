package com.example.demo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.demo.entity.Skill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profiles")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String website;
    private String location;
    private Date joinDate;

    @OneToMany(mappedBy = "profile")
    private Set<Skill> skills = new HashSet<>();

    @OneToMany(mappedBy = "profile")
    private List<Experience> experiences = new ArrayList<>();

    @OneToMany(mappedBy = "profile")
    private List<Education> educations = new ArrayList<>();

    @OneToMany(mappedBy = "profile")
    private List<Social> socials = new ArrayList<>();

    @OneToMany()
    private List<Profile> followings = new ArrayList<>();

    @OneToMany()
    private List<Profile> followers = new ArrayList<>();

    @OneToMany()
    private List<Friend> friends = new ArrayList<>();

    @OneToMany()
    private List<FriendRequest> friendsRequests = new ArrayList<>();

    @OneToMany()
    private List<Post> shares = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<Post> posts = new ArrayList<>();

    @OneToOne(mappedBy = "profile")
    private User user;

}
