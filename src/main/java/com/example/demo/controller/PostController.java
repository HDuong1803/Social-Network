package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.entity.Profile;
import com.example.demo.entity.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.CreateComment;
import com.example.demo.request.CreatePost;
import com.example.demo.request.PostAddDTO;
import com.example.demo.service.PostService;
import com.example.demo.utils.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<?> createProfile(@RequestBody CreatePost req, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Post post = postService.createPost(user.getId(), req);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage("User created successfully", post),
                HttpStatus.OK);

    }

    @GetMapping(value = "/posts/{userId}")
    public ResponseEntity<?> getListPostByUserId(@PathVariable(name = "userId") Long userId) {

        List<Post> posts = postService.getListPostByUserId(userId);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage(posts),
                HttpStatus.OK);
    }

    @GetMapping(value = "/posts/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable(name = "postId") Long postId) {

        Post post = postService.getPostById(postId);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage(post),
                HttpStatus.OK);
    }

    @DeleteMapping(value = "/posts/{postId}")
    public ResponseEntity<?> deletePostId(@PathVariable(name = "postId") Long postId) {

        postService.deletePostById(postId);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Delete successfully"),
                HttpStatus.OK);
    }

    @GetMapping(value = "/posts/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable(name = "postId") Long postId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        List<Profile> res = postService.likePost(user.getId(), postId);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Liked post", res),
                HttpStatus.OK);
    }

    @GetMapping(value = "/posts/{postId}/unlike")
    public ResponseEntity<?> unLikePost(@PathVariable(name = "postId") Long postId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        List<Profile> res = postService.unLikePost(user.getId(), postId);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("UnLiked post", res),
                HttpStatus.OK);
    }

    @GetMapping(value = "/posts/{postId}/share")
    public ResponseEntity<?> sharePost(@PathVariable(name = "postId") Long postId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        List<Post> res = postService.sharePost(user.getId(), postId);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Liked post", res),
                HttpStatus.OK);
    }

    @DeleteMapping(value = "/posts/{postId}/removeShare")
    public ResponseEntity<?> removeSharePost(@PathVariable(name = "postId") Long postId,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        List<Post> res = postService.removeSharePost(user.getId(), postId);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Liked post", res),
                HttpStatus.OK);
    }

    @PostMapping(value = "/posts/{postId}/comment")
    public ResponseEntity<?> addComment(Authentication authentication, CreateComment req) {
        User user = (User) authentication.getPrincipal();

        Comment res = postService.addComment(user.getId(), req);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Liked post", res),
                HttpStatus.OK);
    }

    @DeleteMapping(value = "/posts/{postId}/comment/{id}")
    public ResponseEntity<?> removeComment(@PathVariable(name = "postId") Long postId, Authentication authentication,
            @PathVariable(name = "id") Long id) {
        User user = (User) authentication.getPrincipal();

        postService.removeComment(user.getId(), postId, id);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Liked post"),
                HttpStatus.OK);
    }

}
