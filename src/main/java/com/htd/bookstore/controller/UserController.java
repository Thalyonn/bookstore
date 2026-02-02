package com.htd.bookstore.controller;

import com.htd.bookstore.dto.UserResponse;
import com.htd.bookstore.model.User;
import com.htd.bookstore.repository.UserRepository;
import com.htd.bookstore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * The type User controller.
 */
@Controller
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    /**
     * The User service.
     */
    UserService userService;

    /**
     * Instantiates a new User controller.
     *
     * @param userService    the user service
     * @param userRepository the user repository
     */
    UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * Register user and return response entity of UserResponse.
     *
     * @param payload the payload
     * @return the response entity
     */
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

    /**
     * Current user response entity.
     *
     * @param userDetails the user details
     * @return the response entity
     */
    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.ok(Map.of("authenticated", "false"));
        }
        return ResponseEntity.ok(Map.of(
                "authenticated", "true",
                "username", userDetails.getUsername()
        ));
    }

}
