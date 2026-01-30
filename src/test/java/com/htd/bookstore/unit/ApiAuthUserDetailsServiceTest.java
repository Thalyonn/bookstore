package com.htd.bookstore.unit;

import com.htd.bookstore.repository.UserRepository;
import com.htd.bookstore.service.ApiAuthUserDetailsService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class ApiAuthUserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ApiAuthUserDetailsService userDetailsService;

    @Test
    void contextLoads() {

    }


}
