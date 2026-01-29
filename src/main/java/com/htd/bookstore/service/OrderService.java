package com.htd.bookstore.service;

import com.htd.bookstore.model.CartItem;
import com.htd.bookstore.model.Order;
import com.htd.bookstore.model.OrderItem;
import com.htd.bookstore.model.User;
import com.htd.bookstore.repository.OrderItemRepository;
import com.htd.bookstore.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartService cartService;
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartService = cartService;
    }

    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }
    //to do: clear items in cart after successful checkout
    @Transactional
    public Order checkout(User user) {
        //checkout everything in the users cart
        List<CartItem> cartItems = cartService.getCartItems(user);
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("User cart is empty");
        }
        BigDecimal totalPrice = BigDecimal.ZERO;
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setUser(user);
        order.setStatus("PENDING");
        //create and add individual items to the repository, and connect to order
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getBook().getPrice() );
            totalPrice = totalPrice.add(orderItem.getPrice())
                    .multiply(
                            BigDecimal.valueOf(cartItem.getQuantity())
                    );
            orderItemRepository.save(orderItem);
        }
        order.setTotalAmount(totalPrice);

        return orderRepository.save(order);


    }
}
