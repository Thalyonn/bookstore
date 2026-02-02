package com.htd.bookstore.repository;

import com.htd.bookstore.model.Order;
import com.htd.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface Order repository.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Find by user list.
     *
     * @param user the user
     * @return the list
     */
    List<Order> findByUser(User user);

    /**
     * Find by status list.
     *
     * @param status the status
     * @return the list
     */
    List<Order> findByStatus(String status);
}
