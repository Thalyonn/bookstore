package com.htd.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * The type Security configuration.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    /**
     * Security filter chain security filter chain.
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) //only disabled for dev purposes
                .authorizeHttpRequests(auth -> auth
                        //these endpoints are public
                        .requestMatchers("/api/users/register", "/api/users/me").permitAll()
                        .requestMatchers("/api/categories/**").permitAll()
                        .requestMatchers("/api/books/**").permitAll()
                        .requestMatchers( "/index.html", "/css/**", "/js/**").permitAll()
                        .requestMatchers( "/","/login","/register.html", "/api/users/register").permitAll()

                        //only for USER
                        .requestMatchers("/api/orders/**").hasRole("USER")
                        .requestMatchers("/api/cart/**").hasRole("USER")



                        // Everything else requires authentication
                        .anyRequest().authenticated()
                ).formLogin(
                    form -> form.loginPage("/login")
                            .loginPage("/login.html") //serve the login.html
                            .loginProcessingUrl("/login") //letting spring handle the POST /login
                            .defaultSuccessUrl("/index.html", true)
                            .permitAll()

                )
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

    /**
     * Password encoder password encoder.
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

