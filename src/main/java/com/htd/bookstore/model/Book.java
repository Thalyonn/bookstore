package com.htd.bookstore.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    private String title;
    private String author;
    private String description;
    private BigDecimal price;
    private int stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


}
