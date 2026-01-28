package com.htd.bookstore.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String name;

    @Column(unique = true)
    private String email;

    private String passwordHash;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
