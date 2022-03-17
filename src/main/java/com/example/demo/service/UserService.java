package com.example.demo.service;

import java.util.Optional;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.CreateNewUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean existsByEmail(String email) {

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return false;
        }
        return true;
    }

    public void create(CreateNewUser req) {

        String passwordEncoded = passwordEncoder.encode(req.getPassword());
        User user = User.builder()
                .email(req.getEmail())
                .name(req.getName())
                .phone(req.getPhone())
                .password(passwordEncoded)
                .build();

        userRepository.save(user);
    }

}
