package com.example.demo.repository;

import java.util.Optional;

import com.example.demo.entity.Friend;
import com.example.demo.entity.Profile;

import org.springframework.data.repository.CrudRepository;

public interface FriendRepository extends CrudRepository<Friend, Long> {

    Friend findByProfile(Profile profile);

}
