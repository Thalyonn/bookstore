package com.htd.bookstore.repository;

import com.htd.bookstore.model.ShoppingCart;
import com.htd.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


/**
 * The interface Shopping cart repository.
 */
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    /**
     * Find by user and return optional of shopping carts.
     *
     * @param user the user
     * @return the optional of shopping carts
     */
    Optional<ShoppingCart> findByUser(User user);
}
