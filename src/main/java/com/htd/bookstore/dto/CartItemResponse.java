package com.htd.bookstore.dto;

import com.htd.bookstore.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The type Cart item response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    private Long bookId;
    private String title;
    private String author;
    private int quantity;
    private BigDecimal price;

    /**
     * Instantiates a new Cart item response.
     *
     * @param cartItem the cart item
     */
    public CartItemResponse(CartItem cartItem) {
        this.bookId = cartItem.getBook().getBookId();
        this.title = cartItem.getBook().getTitle();
        this.author = cartItem.getBook().getAuthor();
        this.quantity = cartItem.getQuantity();
        this.price = cartItem.getBook().getPrice();

    }
}
