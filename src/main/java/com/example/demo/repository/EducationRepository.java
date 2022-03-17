package com.example.demo.repository;

import com.example.demo.entity.Education;

import org.springframework.data.repository.CrudRepository;

public interface EducationRepository extends CrudRepository<Education,Long> {
    
}
