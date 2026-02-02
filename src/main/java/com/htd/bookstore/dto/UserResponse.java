package com.htd.bookstore.dto;

import com.htd.bookstore.model.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * The type User response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long userId;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String role;

    /**
     * Instantiates a new User response.
     *
     * @param user the user
     */
    public UserResponse(User user) {
       this.userId = user.getUserId();
       this.username = user.getUsername();
       this.createdAt = user.getCreatedAt();
       this.updatedAt = user.getUpdatedAt();
       this.role = user.getRole();

    }
}
