package com.htd.bookstore.service;

import com.htd.bookstore.model.User;
import com.htd.bookstore.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username,
                             String password
                             ) {
        if (userRepository.findByUsername(username).isPresent())
        { throw new IllegalArgumentException("Username already exists"); }
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole("USER");
        return userRepository.save(user);

    }
}
