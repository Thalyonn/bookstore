package com.htd.bookstore.service;

import com.htd.bookstore.model.User;
import com.htd.bookstore.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
The purpose of this custom implementation is for Spring to be able to use
loadUserByUsername with the data from our repository of users.
 */
@Service
public class ApiAuthUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    public ApiAuthUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPasswordHash())
                .roles(user.getRole())
                .build();
    }

}
