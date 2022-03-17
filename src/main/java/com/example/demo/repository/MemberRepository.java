package com.example.demo.repository;

import com.example.demo.entity.Member;
import com.example.demo.entity.Profile;

import org.springframework.data.repository.CrudRepository;

public interface MemberRepository  extends CrudRepository<Member, Long>{

    Member findByProfile(Profile profile);
    
}
