package com.example.demo.repository;

import com.example.demo.entity.Experience;

import org.springframework.data.repository.CrudRepository;

public interface ExperienceRepository  extends CrudRepository<Experience, Long>{
    
}
