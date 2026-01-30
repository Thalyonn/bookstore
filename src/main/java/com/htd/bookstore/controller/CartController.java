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
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    OrderService orderService;
    CartService cartService;
    UserService userService;
    public CartController(OrderService orderService, CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
        this.orderService = orderService;
    }
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
        ShoppingCart cart = cartService.getCartByUser(user.get());
        //addbooktocart
        CartItem item = cartService.addBookToCart(user.get(), bookId, quantity);
        CartItemResponse cartItemResponse = new CartItemResponse(item);
        return ResponseEntity.ok(item);

    }

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
}
