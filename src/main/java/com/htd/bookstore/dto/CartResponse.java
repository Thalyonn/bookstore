package com.htd.bookstore.dto;

import com.htd.bookstore.model.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Cart response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private Long cartId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String username;
    private List<CartItemResponse> items;

    /**
     * Instantiates a new Cart response.
     *
     * @param shoppingCart the shopping cart
     */
    public CartResponse(ShoppingCart shoppingCart) {
        this.cartId = shoppingCart.getCartId();
        this.createdAt = shoppingCart.getCreatedAt();
        this.updatedAt = shoppingCart.getUpdatedAt();
        this.username = shoppingCart.getUser().getUsername();
        //making cart item response to each item
        this.items = shoppingCart.getItems().stream()
                .map(CartItemResponse::new)
                .collect(Collectors.toList());
    }
}
