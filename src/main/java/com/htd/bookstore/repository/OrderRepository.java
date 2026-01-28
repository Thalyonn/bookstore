package com.htd.bookstore.repository;

import com.htd.bookstore.model.Order;
import com.htd.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    List<Order> findByStatus(String status);
}
