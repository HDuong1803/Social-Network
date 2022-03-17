package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.dto.PasswordDTO;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.UserUpdate;
import com.example.demo.utils.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/users/change_password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordDTO dto) {

        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        if (user.isPresent()) {

            boolean isPasswordCorrect = passwordEncoder.matches(dto.getOldPassword(), user.get().getPassword());
            if (isPasswordCorrect) {
                String encodedPassword = passwordEncoder.encode(dto.getNewPassword());
                user.get().setPassword(encodedPassword);
                userRepository.save(user.get());

                return (ResponseEntity<?>) ResponseEntity.ok();
            }

        }

        return ResponseEntity.status(404).build();

    }

}
