package com.htd.bookstore.repository;

import com.htd.bookstore.model.Order;
import com.htd.bookstore.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface Order item repository.
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    /**
     * Find by order list.
     *
     * @param order the order
     * @return the list
     */
    List<OrderItem> findByOrder(Order order);
}
