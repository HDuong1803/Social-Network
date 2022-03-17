package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.entity.Profile;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.request.CreateComment;
import com.example.demo.request.CreatePost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private CommentRepository commentRepository;

    public Post createPost(Long userId, CreatePost req) {

        Profile profile = profileService.getByUser(userId);
        if (ObjectUtils.isEmpty(profile)) {
            throw new NotFoundException("User id is not exist");
        }

        Post post = Post.builder()
                .text(req.getText())
                .date(new Date())
                .owner(profile)
                .build();

        return postRepository.save(post);

    }

    public Post updatePost(Long postId, CreatePost req) {

        Optional<Post> post = Optional.ofNullable(postRepository.findById(postId).get());
        if (post.isEmpty()) {
            throw new NotFoundException("Post is not found");
        }

        post.get().setText(req.getText());
        post.get().setUpdateDate(new Date());

        return postRepository.save(post.get());

    }

    public List<Post> getAllPost() {
        return (List<Post>) postRepository.findAll();
    }

    public Post getPostById(Long postId) {

        Optional<Post> post = Optional.ofNullable(postRepository.findById(postId).get());
        if (post.isEmpty()) {
            throw new NotFoundException("Post is not found");
        }

        return postRepository.findById(postId).get();

    }

    public List<Post> getListPostByUserId(Long userId) {

        Profile profile = profileService.getByUser(userId);
        if (ObjectUtils.isEmpty(profile)) {
            throw new NotFoundException("User id is not exist");
        }

        List<Post> response = postRepository.findByOwner(profile);

        return response;

    }

    public void deletePostById(Long postId) {

        Optional<Post> post = Optional.ofNullable(postRepository.findById(postId).get());
        if (post.isEmpty()) {
            throw new NotFoundException("Post is not found");
        }

        postRepository.deleteById(postId);

    }

    public List<Profile> likePost(Long userId, Long postId) {

        Optional<Post> post = Optional.ofNullable(postRepository.findById(postId).get());
        if (post.isEmpty()) {
            throw new NotFoundException("Post is not found");
        }

        Profile profile = profileService.getByUser(userId);
        if (ObjectUtils.isEmpty(profile)) {
            throw new NotFoundException("User id is not exist");
        }

        post.get().getLikes().forEach(item -> {
            if (item.getId() == profile.getId()) {
                throw new BadRequestException("Post already liked");
            }
        });

        post.get().getLikes().add(profile);

        postRepository.save(post.get());
        return post.get().getLikes();

    }

    public List<Profile> unLikePost(Long userId, Long postId) {

        Optional<Post> post = Optional.ofNullable(postRepository.findById(postId).get());
        if (post.isEmpty()) {
            throw new NotFoundException("Post is not found");
        }

        Profile profile = profileService.getByUser(userId);
        if (ObjectUtils.isEmpty(profile)) {
            throw new NotFoundException("User id is not exist");
        }

        if (!post.get().getLikes().contains(profile)) {
            throw new BadRequestException("Post has not yet been liked");
        }

        post.get().getLikes().forEach(item -> {
            if (item.getId() == profile.getId()) {
                post.get().getLikes().remove(profile);
            }
        });

        postRepository.save(post.get());
        return post.get().getLikes();

    }

    public Comment addComment(Long userId, CreateComment req) {

        Optional<Post> post = Optional.ofNullable(postRepository.findById(req.getPostId()).get());
        if (post.isEmpty()) {
            throw new NotFoundException("Post is not found");
        }

        Profile profile = profileService.getByUser(userId);
        if (ObjectUtils.isEmpty(profile)) {
            throw new NotFoundException("User id is not exist");
        }

        Comment comment = Comment.builder()
                .text(req.getText())
                .post(post.get())
                .date(new Date())
                .user(profile)
                .build();
        commentRepository.save(comment);
        post.get().getComments().add(comment);
        postRepository.save(post.get());

        return comment;

    }

    public void removeComment(Long userId, Long postId, Long commentId) {

        Optional<Post> post = Optional.ofNullable(postRepository.findById(postId).get());
        if (post.isEmpty()) {
            throw new NotFoundException("Post is not found");
        }

        Profile profile = profileService.getByUser(userId);
        if (ObjectUtils.isEmpty(profile)) {
            throw new NotFoundException("User id is not exist");
        }

        Comment comment = commentRepository.findById(commentId).get();
        if (ObjectUtils.isEmpty(comment)) {
            throw new NotFoundException("Comment is not exist");
        }

        commentRepository.deleteById(commentId);
        post.get().getComments().remove(comment);
        postRepository.save(post.get());

    }

    public List<Post> sharePost(Long userId, Long postId) {

        Optional<Post> post = Optional.ofNullable(postRepository.findById(postId).get());
        if (post.isEmpty()) {
            throw new NotFoundException("Post is not found");
        }

        Profile profile = profileService.getByUser(userId);
        if (ObjectUtils.isEmpty(profile)) {
            throw new NotFoundException("User id is not exist");
        }

        if (profile.getShares().contains(post.get())) {
            throw new BadRequestException("Post already shared");
        }

        profile.getShares().add(post.get());
        profileService.save(profile);
        return profile.getShares();

    }

    public List<Post> removeSharePost(Long userId, Long postId) {

        Optional<Post> post = Optional.ofNullable(postRepository.findById(postId).get());
        if (post.isEmpty()) {
            throw new NotFoundException("Post is not found");
        }

        Profile profile = profileService.getByUser(userId);
        if (ObjectUtils.isEmpty(profile)) {
            throw new NotFoundException("User id is not exist");
        }

        if (!profile.getShares().contains(post.get())) {
            throw new BadRequestException("'Post has not yet been shared");
        }

        profile.getShares().remove(post.get());
        profileService.save(profile);
        return profile.getShares();

    }

    

}
