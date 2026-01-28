package com.htd.bookstore.repository;

import com.htd.bookstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

interface OrderRepository extends JpaRepository<Order, Integer> {
}
