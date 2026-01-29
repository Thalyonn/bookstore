package com.htd.bookstore.dto;

import com.htd.bookstore.model.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private Long cartId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String username;
    private List<CartItemResponse> items;

    public CartResponse(ShoppingCart shoppingCart) {
        this.cartId = shoppingCart.getCartId();
        this.createdAt = shoppingCart.getCreatedAt();
        this.updatedAt = shoppingCart.getUpdatedAt();
        this.username = shoppingCart.getUser().getUsername();
        //making cart item response to each item
        this.items = shoppingCart.getItems().stream()
                .map(item -> new CartItemResponse(
                        item.getBook().getBookId(),
                        item.getBook().getTitle(),
                        item.getQuantity(),
                        item.getBook().getPrice() ))
                .collect(Collectors.toList());
    }
}
