package com.example.demo.repository;

import com.example.demo.entity.Manager;
import com.example.demo.entity.Profile;

import org.springframework.data.repository.CrudRepository;

public interface ManagerRepository extends CrudRepository<Manager, Long> {

    Manager findByProfile(Profile profile);
    
}
