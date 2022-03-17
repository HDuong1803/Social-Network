package com.example.demo.repository;

import com.example.demo.entity.Group;

import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Long> {

    Group findByNameAndCode(String name, Integer code);
    
}
