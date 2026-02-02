package com.htd.bookstore.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * The type Order item.
 */
@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    private int quantity;
    private BigDecimal price; //this will just be book price at time of ordering

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
