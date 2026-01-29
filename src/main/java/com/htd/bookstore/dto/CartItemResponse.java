package com.htd.bookstore.dto;

import com.htd.bookstore.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    private Long bookId;
    private String title;
    private int quantity;
    private BigDecimal price;

    public CartItemResponse(CartItem cartItem) {
        this.bookId = cartItem.getBook().getBookId();
        this.title = cartItem.getBook().getTitle();
        this.quantity = cartItem.getQuantity();
        this.price = cartItem.getBook().getPrice();

    }
}
