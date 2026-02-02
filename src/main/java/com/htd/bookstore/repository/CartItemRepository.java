package com.htd.bookstore.repository;

import com.htd.bookstore.model.Book;
import com.htd.bookstore.model.CartItem;
import com.htd.bookstore.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Cart item repository.
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    /**
     * Find by cart list.
     *
     * @param cart the cart
     * @return the list
     */
    List<CartItem> findByCart(ShoppingCart cart);

    /**
     * Find by cart and book optional.
     *
     * @param cart the cart
     * @param book the book
     * @return the optional
     */
    Optional<CartItem> findByCartAndBook(ShoppingCart cart, Book book);

    /**
     * Delete by cart.
     *
     * @param cart the cart
     */
    void deleteByCart(ShoppingCart cart);
}
