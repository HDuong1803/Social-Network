package com.example.demo.repository;

import com.example.demo.entity.MemberRequest;

import org.springframework.data.repository.CrudRepository;

public interface MemberRequestRepository extends CrudRepository<MemberRequest, Long> {
    
}
