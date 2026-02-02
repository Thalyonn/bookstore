package com.htd.bookstore.service;

import com.htd.bookstore.model.*;
import com.htd.bookstore.repository.BookRepository;
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
    private final BookRepository bookRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, CartService cartService, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartService = cartService;
        this.bookRepository = bookRepository;
    }

    /**
     * Gets order of the user.
     * @param user The user whose orders are gotten.
     * @return The List<Order> of user orders.
     */
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    /**
     * Checkout the cart items and turns them into an order.
     * @param user The user whose cart will be checked-out.
     * @return The order of the user.
     */
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
            Book book = cartItem.getBook();
            if(cartItem.getQuantity() > book.getStock()) {
                throw new IllegalStateException("Not enough stock.");
            }
            book.setStock(book.getStock() - cartItem.getQuantity());
            bookRepository.save(book);
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(book);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getBook().getPrice() );
            totalPrice = totalPrice.add(orderItem.getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            orderItemRepository.save(orderItem);
            order.getItems().add(orderItem);
        }
        order.setTotalAmount(totalPrice);
        Order returnOrder = orderRepository.save(order);
        cartService.clearCart(user);
        return returnOrder;


    }
}
