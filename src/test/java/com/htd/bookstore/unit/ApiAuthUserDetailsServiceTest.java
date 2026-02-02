package com.htd.bookstore.unit;

import com.htd.bookstore.model.User;
import com.htd.bookstore.repository.UserRepository;
import com.htd.bookstore.service.ApiAuthUserDetailsService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiAuthUserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ApiAuthUserDetailsService userDetailsService;

    @Test
    void returnUserDetailsWhenUserExists() {
        User user = new User();
        user.setUsername("Bingo");
        //the hashed password
        user.setPasswordHash("123hash456");
        user.setRole("USER");
        //when userRepository tries to find Bingo then it returns the user Bingo.
        when(userRepository.findByUsername("Bingo")).thenReturn(Optional.of(user));

        UserDetails result = userDetailsService.loadUserByUsername("Bingo");

        //assert that user matches with the result UserDetails
        assertEquals("Bingo", result.getUsername());
        assertEquals("123hash456", result.getPassword());
        //Spring Security adds ROLE_ at the start of the string
        //  when getting roles with getAuthorities
        assertEquals("ROLE_USER", result.getAuthorities().iterator().next().getAuthority());
        //verify that userRespository.findByUsername is called with Bingo
        verify(userRepository).findByUsername("Bingo");

    }

    @Test
    void throwUsernameNotFoundExceptionWhenUserDoesNotExist() {
        when(userRepository.findByUsername("NotExists")).thenReturn(Optional.empty());

        UsernameNotFoundException exception =  assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("NotExists")
        );
        //assert the exception and verify that findByUsername was not called
        assertEquals("User not found", exception.getMessage());
        verify(userRepository).findByUsername("NotExists");
    }


}
