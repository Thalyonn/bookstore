package com.htd.bookstore.service;

import com.htd.bookstore.model.User;
import com.htd.bookstore.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * This is a service for managing some user operations such as registration, and getting users.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user into the user repository.
     * @param username
     * @param password
     * @return the user that was saved to the repository
     * @throws IllegalArgumentException if username already exists in database
     */
    @Transactional
    public User registerUser(String username,
                             String password
                             ) {
        if (userRepository.findByUsername(username).isPresent())
        { throw new IllegalArgumentException("Username already exists"); }
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setCreatedAt(LocalDateTime.now());
        user.setRole("USER");
        return userRepository.save(user);

    }

    /**
     * Gets a user given a username.
     * @param username
     * @return an Optional of User based on username found in user repository
     */
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
