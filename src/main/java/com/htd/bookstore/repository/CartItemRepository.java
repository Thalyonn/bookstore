package com.htd.bookstore.repository;

import com.htd.bookstore.model.Book;
import com.htd.bookstore.model.CartItem;
import com.htd.bookstore.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(ShoppingCart cart);
    Optional<CartItem> findByCartAndBook(ShoppingCart cart, Book book);
    void deleteByCart(ShoppingCart cart);
}
