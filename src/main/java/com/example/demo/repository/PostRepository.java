package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Post;
import com.example.demo.entity.Profile;

import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findByOwner(Profile profile);

}
