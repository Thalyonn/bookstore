package com.htd.bookstore.repository;

import com.htd.bookstore.model.CartItem;
import com.htd.bookstore.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCart(ShoppingCart cart);
}
