package com.htd.bookstore.repository;

import com.htd.bookstore.model.Order;
import com.htd.bookstore.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrder(Order order);
}
