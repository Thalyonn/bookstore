package com.htd.bookstore.controller;

import com.htd.bookstore.dto.CartItemResponse;
import com.htd.bookstore.dto.CartResponse;
import com.htd.bookstore.dto.OrderResponse;
import com.htd.bookstore.model.*;
import com.htd.bookstore.service.CartService;
import com.htd.bookstore.service.OrderService;
import com.htd.bookstore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Map;
import java.util.Optional;

/**
 * The type Cart controller.
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {
    /**
     * The Order service.
     */
    OrderService orderService;
    /**
     * The Cart service.
     */
    CartService cartService;
    /**
     * The User service.
     */
    UserService userService;

    /**
     * Instantiates a new Cart controller.
     *
     * @param orderService the order service
     * @param cartService  the cart service
     * @param userService  the user service
     */
    public CartController(OrderService orderService, CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
        this.orderService = orderService;
    }

    /**
     * Add book to cart response entity.
     *
     * @param bookId      the book id
     * @param quantity    the quantity
     * @param userDetails the user details
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<?> addBookToCart(@RequestParam Long bookId,
                                           @RequestParam int quantity,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<User> user = userService.getUserByUsername(userDetails.getUsername());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        //check for negative quantity
        if (quantity <= 0) { return ResponseEntity.badRequest()
                .body(Map.of("error", "Quantity must be greater than zero")); }
        ShoppingCart cart = cartService.getCartByUser(user.get());
        //addbooktocart
        CartItem item = cartService.addBookToCart(user.get(), bookId, quantity);
        CartItemResponse cartItemResponse = new CartItemResponse(item);
        return ResponseEntity.ok(cartItemResponse);

    }

    /**
     * Gets cart by user.
     *
     * @param userDetails the user details
     * @return the cart by user
     */
    @GetMapping
    public ResponseEntity<?> getCartByUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Optional<User> user = userService.getUserByUsername(userDetails.getUsername());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        ShoppingCart cart = cartService.getCartByUser(user.get());
        CartResponse response = new CartResponse(cart);
        return ResponseEntity.ok(response);
    }

    /**
     * Checkout cart response entity.
     *
     * @param userDetails the user details
     * @return the response entity
     */
    @PostMapping("/checkout")
    public ResponseEntity<?> checkoutCart(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Optional<User> user = userService.getUserByUsername(userDetails.getUsername());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Order order = orderService.checkout(user.get());
        OrderResponse orderResponse = new OrderResponse(order);
        return ResponseEntity.ok(orderResponse);
    }

    /**
     * Delete cart and return cart response entity which is a CartResponse.
     *
     * @param userDetails the user details
     * @return the response entity CartResponse
     */
    @DeleteMapping
    public ResponseEntity<?> deleteCart(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Optional<User> user = userService.getUserByUsername(userDetails.getUsername());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        ShoppingCart cart = cartService.getCartByUser(user.get());
        cartService.clearCart(user.get());
        return ResponseEntity.ok(new CartResponse(cart));
    }
}
