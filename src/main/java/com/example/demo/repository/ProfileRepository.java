package com.example.demo.repository;

import com.example.demo.entity.Profile;
import com.example.demo.entity.User;

import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long>{

    Profile findByUser(User user);
    
}
