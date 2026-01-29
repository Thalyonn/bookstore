package com.htd.bookstore.controller;

import com.htd.bookstore.dto.UserResponse;
import com.htd.bookstore.model.User;
import com.htd.bookstore.repository.UserRepository;
import com.htd.bookstore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/api/users")
class UserController {
    private final UserRepository userRepository;
    UserService userService;
    UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> payload) {
        UserResponse userResponse;
        try {
            System.out.println("registering user " +  payload.get("username"));
            User newUser = userService.registerUser(payload.get("username"), payload.get("password"));
            userResponse = new UserResponse(newUser.getUserId(), newUser.getUsername(), newUser.getCreatedAt(), newUser.getUpdatedAt(), newUser.getRole());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(userResponse);

    }
}
