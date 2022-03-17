package com.example.demo.repository;

import com.example.demo.entity.FriendRequest;
import com.example.demo.entity.Profile;

import org.springframework.data.repository.CrudRepository;

public interface FriendRequestRepository  extends CrudRepository<FriendRequest, Long>{

    FriendRequest findByProfile(Profile profile);
    
}
