package com.htd.bookstore.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private ShoppingCart cart;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
