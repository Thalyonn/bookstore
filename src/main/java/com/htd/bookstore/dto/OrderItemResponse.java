package com.htd.bookstore.dto;

import com.htd.bookstore.model.Book;
import com.htd.bookstore.model.Order;
import com.htd.bookstore.model.OrderItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The type Order item response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {

    private Long orderItemId;
    private String title;
    private String author;
    private int quantity;
    private BigDecimal unitPrice;

    /**
     * Instantiates a new Order item response.
     *
     * @param orderItem the order item
     */
    public OrderItemResponse(OrderItem orderItem) {
        this.orderItemId = orderItem.getOrderItemId();
        this.title = orderItem.getBook().getTitle();
        this.author = orderItem.getBook().getAuthor();
        this.quantity = orderItem.getQuantity();
        this.unitPrice = orderItem.getBook().getPrice();
    }
}
