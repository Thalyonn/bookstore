package com.htd.bookstore.repository;

import com.htd.bookstore.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;


interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
}
