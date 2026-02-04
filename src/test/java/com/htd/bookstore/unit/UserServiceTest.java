package com.htd.bookstore.unit;

import com.htd.bookstore.model.User;
import com.htd.bookstore.repository.UserRepository;
import com.htd.bookstore.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @Test
    void contextLoads() {

    }

    @Test
    void getUserByUsernameExists() {
        User user = new User();
        user.setUsername("bingo");
        user.setPasswordHash("123456");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByUsername("bingo");
        assertTrue(result.isPresent());
        User resultUser = result.get();
        assertEquals("bingo" , resultUser.getUsername());
    }
    @Test
    void getUserByUsernameNotExists() {
        //when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());
        Optional<User> result = userService.getUserByUsername("bingo");
        assertFalse(result.isPresent());
    }

    @Test
    void registerNewUserUsernameNotExists() {
        String username = "bingo";
        String password = "123456";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        String passwordHash = "$asdasdadsadasdnsdjn$$sds$";
        when(passwordEncoder.encode(password)).thenReturn(passwordHash);
        //return same user when saving a User
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        User user = userService.registerUser(username, password, "USER");

        assertTrue(user.getUsername().equals(username));
        assertFalse(password.equals(user.getPasswordHash()));
        assertTrue(passwordHash.equals(user.getPasswordHash()));
    }

    @Test
    void registerNewUserUsernameExists() {
        String username = "bingo";
        String password = "123456";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));



        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(username, password, "USER");
        });

    }


}
