package com.htd.bookstore.controller;

import com.htd.bookstore.dto.OrderResponse;
import com.htd.bookstore.model.Order;
import com.htd.bookstore.model.User;
import com.htd.bookstore.service.CartService;
import com.htd.bookstore.service.OrderService;
import com.htd.bookstore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

/**
 * The type Order controller.
 */
@RestController
@RequestMapping("/api/orders")
class OrderController {
    /**
     * The Order service.
     */
    OrderService orderService;
    /**
     * The User service.
     */
    UserService userService;

    /**
     * Instantiates a new Order controller.
     *
     * @param orderService the order service
     * @param userService  the user service
     */
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    /**
     * Gets orders.
     *
     * @param userDetails the user details
     * @return the orders in the form of an order response
     */
    @GetMapping
    public ResponseEntity<?> getOrders(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<User> user = userService.getUserByUsername(userDetails.getUsername());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Order> orders = orderService.getOrdersByUser(user.get());

        return ResponseEntity.ok(orders.stream().map(OrderResponse::new).toList());
    }

}
